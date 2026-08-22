package com.sidephone.snake.engine.entities

import android.util.Log
import com.sidephone.snake.engine.graphics.DrawCommand
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt


class Snake {
	companion object {
		private val LOG_TAG = Snake::class.java.simpleName

		const val INITIAL_LENGTH = 3.5
		const val MOVE_SPEED = Segment.RADIUS * 2 // px per iteration

		object Segment {
			const val RADIUS = 10f
			const val RADIUS_MIN = RADIUS * 0.4f
		}

		object Tongue {
			const val LENGTH_NORMAL = Segment.RADIUS * 0.8f
			const val LENGTH_LONG = LENGTH_NORMAL * 3
			const val FORK_LENGTH = Segment.RADIUS * 0.25f
			const val STICK_OUT_BASE_TIME = 500L // milliseconds
			const val STICK_OUT_LONG_TIME = (STICK_OUT_BASE_TIME * 1.55).toLong() // milliseconds

			const val STICK_OUT_FORCED_PROBABILITY = 0.3 // 30% chance
			const val STICK_OUT_NATURALLY_PROBABILITY = 0.15 // 15% chance
		}

		object Colors {
			const val HEAD: Int = 0xFFB7F34A.toInt()
			const val BODY: Int = 0xFF72C93D.toInt()
			const val TONGUE: Int = 0xFFFF3935.toInt()
		}
	}

	enum class Direction { UP, DOWN, LEFT, RIGHT }

	private var direction = Direction.UP
	private var segments = mutableListOf<Pair<Float, Float>>() // List of (x, y) positions of the snake segments
	private var length = INITIAL_LENGTH
	private var isAlive = true

	private var tongueTimeout = -1L
	private var isTongueLong = false


	fun isAlive(screenWidth: Float, screenHeight: Float): Boolean {
		val head = segments.firstOrNull() ?: return false

		if (head.first !in 0f..screenWidth || head.second !in 0f..screenHeight) {
			isAlive = false
		}

		for (i in 1 until segments.size) {
			if (head == segments[i]) {
				isAlive = false
				break
			}
		}

		return isAlive
	}


	fun segments(): Int {
		return ceil(length).toInt()
	}


	/**
	 * Create a newborn snake at a random position, heading parallel to the closest screen edge,
	 * and away from the second-closest edge, and a length of INITIAL_LENGTH segments.
	 */
	fun spawn(screenWidth: Float, screenHeight: Float) {
		isAlive = true

		// head position
		val startX = (Segment.RADIUS * 2f) + (Math.random() * (screenWidth - Segment.RADIUS * 4)).toFloat()
		val startY = (Segment.RADIUS * 2f) + (Math.random() * (screenHeight - Segment.RADIUS * 4)).toFloat()
		direction = when {
			startX < screenWidth / 2 && startY < screenHeight / 2 -> Direction.RIGHT // Top-left quadrant
			startX >= screenWidth / 2 && startY < screenHeight / 2 -> Direction.DOWN // Top-right quadrant
			startX < screenWidth / 2 && startY >= screenHeight / 2 -> Direction.UP // Bottom-left quadrant
			else -> Direction.LEFT // Bottom-right quadrant
		}

		// head segment
		segments.add(Pair(startX, startY))

		// body
		length = INITIAL_LENGTH
		segments.clear()

		for (i in 1 until 1 + ceil(length).toInt()) {
			val newSegment = when (direction) {
				Direction.UP -> Pair(startX, startY + (i * MOVE_SPEED))
				Direction.DOWN -> Pair(startX, startY - (i * MOVE_SPEED))
				Direction.LEFT -> Pair(startX + (i * MOVE_SPEED), startY)
				Direction.RIGHT -> Pair(startX - (i * MOVE_SPEED), startY)
			}
			segments.add(newSegment)
		}
	}

	fun move() {
		if (segments.isEmpty() || !isAlive) {
			return
		}

		val currentLength = segments.size
		val head = segments.first()
		val speed = if (isTongueLong) MOVE_SPEED * 0.5f else MOVE_SPEED

		val newHead = when (direction) {
			Direction.UP -> Pair(head.first, head.second - speed)
			Direction.DOWN -> Pair(head.first, head.second + speed)
			Direction.LEFT -> Pair(head.first - speed, head.second)
			Direction.RIGHT -> Pair(head.first + speed, head.second)
		}

		// Add the new head position to the front of the list
		segments.add(0, newHead)

		// Remove the last segment to simulate movement
		if (segments.size > currentLength) {
			segments.removeAt(segments.size - 1)
		}
	}


	fun turn(direction: Direction) {
		when (this.direction) {
			Direction.UP -> if (direction != Direction.DOWN) this.direction = direction
			Direction.DOWN -> if (direction != Direction.UP) this.direction = direction
			Direction.LEFT -> if (direction != Direction.RIGHT) this.direction = direction
			Direction.RIGHT -> if (direction != Direction.LEFT) this.direction = direction
		}
	}


	fun toggleTongueRandomly(now: Long, isTurning: Boolean) {
		if (isTongueOut(now)) {
			return
		}

		isTongueLong = false

		val chance = Math.random()
		if (
			(isTurning && chance < Tongue.STICK_OUT_FORCED_PROBABILITY)
			|| (!isTurning && chance < Tongue.STICK_OUT_NATURALLY_PROBABILITY)
		) {
			val duration = (Math.random() * Tongue.STICK_OUT_BASE_TIME).toLong().coerceAtLeast(1L)
			tongueTimeout = now + duration
		}
	}


	fun isTongueOut(now: Long): Boolean {
		return tongueTimeout - now > 0
	}


	fun canEat(food: Food): Boolean {
		val head = segments.firstOrNull() ?: Pair(0f, 0f)
		val foodPos = food.position()
		val distanceX = head.first - foodPos.first
		val distanceY = head.second - foodPos.second

		val distance = sqrt((distanceX * distanceX + distanceY * distanceY).toDouble())
		return distance <= Segment.RADIUS + food.radius()
	}


	fun eat(food: Food, now: Long) {
		length = (length + food.amount()).coerceAtLeast(0.0)

		val newSegmentCount = ceil(length)

		if (newSegmentCount <= 0) {
			segments.clear()
			isAlive = false
		} else if (segments.size < newSegmentCount) {
			while (segments.size < newSegmentCount) {
				segments.add(segments.last())
			}
		} else if (segments.size > newSegmentCount) {
			while (segments.size > newSegmentCount) {
				segments.removeAt(segments.size - 1)
			}

			isTongueLong = true
			tongueTimeout = now + Tongue.STICK_OUT_LONG_TIME
		}

		Log.d(LOG_TAG, "Snake ate ${food.amount()}. New length: $length, segments: ${segments.size}")
	}


	fun draw(now: Long): List<DrawCommand> {
		return drawBody() + drawTongue(now)
	}


	private fun drawBody(): List<DrawCommand> {
		val drawCommands = mutableListOf<DrawCommand>()

		for (i in segments.size - 1 downTo 0) {
			var radius = Segment.RADIUS
			if (i == segments.size - 1) {
				val lengthExp = floor(length)
				val radiusMultiplier = if (lengthExp == length) 1 else (length - lengthExp)
				radius = (Segment.RADIUS * radiusMultiplier.toFloat()).coerceAtLeast(Segment.RADIUS_MIN)
			}

			val color = if (i == 0) Colors.HEAD else Colors.BODY
			val (x, y) = segments[i]

			drawCommands.add(DrawCommand.Circle(x, y, radius, color, true))
		}

		return drawCommands
	}


	fun drawTongue(now: Long): List<DrawCommand> {
		if (!isTongueOut(now)) {
			return emptyList()
		}

		val (x, y) = segments[0]
		val length = if (isTongueLong) Tongue.LENGTH_LONG else Tongue.LENGTH_NORMAL

		val drawCommands = mutableListOf<DrawCommand>()

		when (direction) {
			Direction.UP -> {
				val tipY = y - Segment.RADIUS - length

				drawCommands.add(
					DrawCommand.Line(
						x, y - Segment.RADIUS,
						x, tipY,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						x, tipY,
						x - Tongue.FORK_LENGTH, tipY - Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						x, tipY,
						x + Tongue.FORK_LENGTH, tipY - Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)
			}

			Direction.DOWN -> {
				val tipY = y + Segment.RADIUS + length

				drawCommands.add(
					DrawCommand.Line(
						x, y + Segment.RADIUS,
						x, tipY,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						x, tipY,
						x - Tongue.FORK_LENGTH, tipY + Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						x, tipY,
						x + Tongue.FORK_LENGTH, tipY + Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)
			}

			Direction.LEFT -> {
				val tipX = x - Segment.RADIUS - length

				drawCommands.add(
					DrawCommand.Line(
						x - Segment.RADIUS, y,
						tipX, y,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						tipX, y,
						tipX - Tongue.FORK_LENGTH, y - Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						tipX, y,
						tipX - Tongue.FORK_LENGTH, y + Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)
			}

			Direction.RIGHT -> {
				val tipX = x + Segment.RADIUS + length

				drawCommands.add(
					DrawCommand.Line(
						x + Segment.RADIUS, y,
						tipX, y,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						tipX, y,
						tipX + Tongue.FORK_LENGTH, y - Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)

				drawCommands.add(
					DrawCommand.Line(
						tipX, y,
						tipX + Tongue.FORK_LENGTH, y + Tongue.FORK_LENGTH,
						Colors.TONGUE
					)
				)
			}
		}

		return drawCommands
	}
}
