package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Pineapple : FoodTypeInterface {
	const val PINEAPPLE_COLOR: Int = 0xFFFFC83D.toInt()
	const val PINEAPPLE_DARK: Int = 0xFFD99E24.toInt()
	const val PINEAPPLE_LEAF: Int = 0xFF4E9F45.toInt()
	const val PINEAPPLE_HIGHLIGHT: Int = 0xFFFFE477.toInt()

	const val AMOUNT = 1.25f
	private val RADIUS = Food.BASE_RADIUS * 1.3f + Math.random().toFloat() * 0.75f


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return RADIUS
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Main body.
			DrawCommand.Circle(
				cx = x,
				cy = y + RADIUS * 0.15f,
				radius = RADIUS * 0.72f,
				color = PINEAPPLE_COLOR,
				filled = true
			),

			// Slightly darker bottom portion.
			DrawCommand.Circle(
				cx = x,
				cy = y + RADIUS * 0.38f,
				radius = RADIUS * 0.48f,
				color = PINEAPPLE_DARK,
				filled = true
			),

			// Diamond-like texture.
			DrawCommand.Line(
				x1 = x - RADIUS * 0.5f,
				y1 = y - RADIUS * 0.25f,
				x2 = x + RADIUS * 0.45f,
				y2 = y + RADIUS * 0.45f,
				color = PINEAPPLE_DARK
			),

			DrawCommand.Line(
				x1 = x - RADIUS * 0.45f,
				y1 = y + RADIUS * 0.35f,
				x2 = x + RADIUS * 0.5f,
				y2 = y - RADIUS * 0.2f,
				color = PINEAPPLE_DARK
			),

			DrawCommand.Line(
				x1 = x - RADIUS * 0.65f,
				y1 = y,
				x2 = x + RADIUS * 0.35f,
				y2 = y + RADIUS * 0.65f,
				color = PINEAPPLE_DARK
			),

			// Leafy crown.
			DrawCommand.Line(
				x1 = x,
				y1 = y - RADIUS * 0.4f,
				x2 = x - RADIUS * 0.35f,
				y2 = y - RADIUS * 0.95f,
				color = PINEAPPLE_LEAF
			),

			DrawCommand.Line(
				x1 = x,
				y1 = y - RADIUS * 0.4f,
				x2 = x,
				y2 = y - RADIUS * 1.05f,
				color = PINEAPPLE_LEAF
			),

			DrawCommand.Line(
				x1 = x,
				y1 = y - RADIUS * 0.4f,
				x2 = x + RADIUS * 0.38f,
				y2 = y - RADIUS * 0.9f,
				color = PINEAPPLE_LEAF
			),

			// Small highlight.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.3f,
				cy = y - RADIUS * 0.05f,
				radius = RADIUS * 0.1f,
				color = PINEAPPLE_HIGHLIGHT,
				filled = true
			)
		)
	}
}
