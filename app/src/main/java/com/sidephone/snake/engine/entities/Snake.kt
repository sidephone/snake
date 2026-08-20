package com.sidephone.snake.engine.entities

import android.util.Log
import com.sidephone.snake.engine.graphics.DrawCommand
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt


class Snake {
	private val LOG_TAG = Snake::class.java.simpleName

	enum class Direction { UP, DOWN, LEFT, RIGHT }

	companion object {
		const val INITIAL_LENGTH = 3.5
		const val SEGMENT_RADIUS = 10f
		const val SEGMENT_RADIUS_MIN = SEGMENT_RADIUS * 0.4f
		const val MOVE_SPEED = SEGMENT_RADIUS * 2 // px per iteration

		object Colors {
			const val HEAD: Int = 0xFFFFFF00.toInt()
			const val BODY: Int = 0xFFFFAA00.toInt()
		}
	}

	private var direction = Direction.UP
	private var segments = mutableListOf<Pair<Float, Float>>() // List of (x, y) positions of the snake segments
	private var length = INITIAL_LENGTH
	private var isAlive = true


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
		val startX = (SEGMENT_RADIUS * 2f) + (Math.random() * (screenWidth - SEGMENT_RADIUS * 4)).toFloat()
		val startY = (SEGMENT_RADIUS * 2f) + (Math.random() * (screenHeight - SEGMENT_RADIUS * 4)).toFloat()
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
		val newHead = when (direction) {
			Direction.UP -> Pair(head.first, head.second - MOVE_SPEED)
			Direction.DOWN -> Pair(head.first, head.second + MOVE_SPEED)
			Direction.LEFT -> Pair(head.first - MOVE_SPEED, head.second)
			Direction.RIGHT -> Pair(head.first + MOVE_SPEED, head.second)
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


	fun canEat(food: Food): Boolean {
		val head = segments.firstOrNull() ?: Pair(0f, 0f)
		val foodPos = food.position()
		val distanceX = head.first - foodPos.first
		val distanceY = head.second - foodPos.second

		val distance = sqrt((distanceX * distanceX + distanceY * distanceY).toDouble())
		return distance <= SEGMENT_RADIUS + food.radius()
	}


	fun eat(food: Food) {
		length += food.amount()
		val newSegmentCount = ceil(length)
		while (segments.size < newSegmentCount) {
			segments.add(segments.last())
		}

		Log.d(LOG_TAG, "Snake ate ${food.amount()}. New length: $length, segments: ${segments.size}")
	}


	fun draw(): List<DrawCommand> {
		val drawCommands = mutableListOf<DrawCommand>()

		for (i in segments.size - 1 downTo 0) {
			var radius = SEGMENT_RADIUS
			if (i == segments.size - 1) {
				val lengthExp = floor(length)
				val radiusMultiplier = if (lengthExp == length) 1 else (length - lengthExp)
				radius = (SEGMENT_RADIUS * radiusMultiplier.toFloat()).coerceAtLeast(SEGMENT_RADIUS_MIN)
			}

			val color = if (i == 0) Colors.HEAD else Colors.BODY
			val (x, y) = segments[i]

			drawCommands.add(DrawCommand.Circle(x, y, radius, color, true))
		}

		return drawCommands
	}
}
