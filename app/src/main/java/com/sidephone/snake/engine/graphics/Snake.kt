package com.sidephone.snake.engine.graphics

/**
 * An example on how to use the DrawCommand class to draw a spaceship. You can use this as a reference
 * to create your own game objects.
 */
class Snake {
	enum class Direction { UP, DOWN, LEFT, RIGHT }

	companion object {
		const val INITIAL_LENGTH = 4
		const val SEGMENT_RADIUS: Float = 8f
		const val MOVE_SPEED = SEGMENT_RADIUS * 2 // px per iteration

		object Colors {
			const val HEAD: Int = 0xFFFFFF00.toInt()
			const val BODY: Int = 0xFFFFAA00.toInt()
		}
	}

	private var direction = Direction.UP
	private var segments = mutableListOf<Pair<Float, Float>>() // List of (x, y) positions of the snake segments
	private var isAlive = true


	/**
	 * Create a newborn snake at a random position, heading parallel to the closest screen edge,
	 * and away from the second-closest edge, and a length of INITIAL_LENGTH segments.
	 */
	fun create(screenWidth: Float, screenHeight: Float) {
		isAlive = true
		segments.clear()

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
		for (i in 1 until INITIAL_LENGTH) {
			val newSegment = when (direction) {
			Direction.UP -> Pair(startX, startY + (i * MOVE_SPEED))
			Direction.DOWN -> Pair(startX, startY - (i * MOVE_SPEED))
			Direction.LEFT -> Pair(startX + (i * MOVE_SPEED), startY)
			Direction.RIGHT -> Pair(startX - (i * MOVE_SPEED), startY)
			}
			segments.add(newSegment)
		}
	}


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


	fun draw(): List<DrawCommand> {
		val drawCommands = mutableListOf<DrawCommand>()

		var color = Colors.HEAD
		for ((x, y) in segments) {
			drawCommands.add(DrawCommand.Circle(x, y, SEGMENT_RADIUS, color, true))
			color = Colors.BODY
		}

		return drawCommands
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
}
