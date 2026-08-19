package com.sidephone.snake.engine.entities

import com.sidephone.snake.engine.graphics.DrawCommand

class Food {
	companion object {
		const val COLOR: Int = 0xFF00FF00.toInt()
	}

	private var amount = 0f
	private var radius = 0f
	private var position: Pair<Float, Float> = Pair(0f, 0f)


	fun amount(): Float { return amount }
	fun exists(): Boolean { return amount > 0f }
	fun position(): Pair<Float, Float> { return position }
	fun radius(): Float { return radius }


	fun destroy() {
		amount = 0f
	}


	fun spawn(screenWidth: Float, screenHeight: Float) {
		amount = 0.5f + Math.random().toFloat() * 1.5f
		radius = amount * Snake.SEGMENT_RADIUS

		val maxX = (screenWidth - radius * 4f).coerceAtLeast(0f)
		val maxY = (screenHeight - radius * 4f).coerceAtLeast(0f)
		val x = radius * 2f + (Math.random().toFloat() * maxX)
		val y = radius * 2f + (Math.random().toFloat() * maxY)

		position = Pair(x, y)
	}


	fun draw(): List<DrawCommand> {
		if (amount == 0f) return emptyList()

		return listOf(
			DrawCommand.Circle(
				cx = position.first,
				cy = position.second,
				radius = amount * Snake.SEGMENT_RADIUS,
				color = COLOR,
				filled = true
			)
		)
	}
}
