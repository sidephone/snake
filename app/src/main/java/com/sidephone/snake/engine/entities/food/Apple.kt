package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Apple : FoodTypeInterface {
	const val APPLE_COLOR: Int = 0xFFFF5C5C.toInt()
	const val APPLE_HIGHLIGHT: Int = 0xFFFF7770.toInt()
	const val APPLE_INDENT: Int = 0xFFB83F42.toInt()
	const val STEM_COLOR: Int = 0xFF6B4528.toInt()
	const val LEAF_COLOR: Int = 0xFF72C93D.toInt()

	const val AMOUNT = 0.8f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 0.9f + Math.random().toFloat() * 0.45f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		val small = radius * 0.25f

		return listOf(
			// Apple body.
			DrawCommand.Circle(
				cx = x - radius * 0.28f,
				cy = y + radius * 0.05f,
				radius = radius * 0.72f,
				color = APPLE_COLOR,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.28f,
				cy = y + radius * 0.05f,
				radius = radius * 0.72f,
				color = APPLE_COLOR,
				filled = true
			),

			// Indentation.
			DrawCommand.Circle(
				cx = x,
				cy = y - radius * 0.48f,
				radius = small,
				color = APPLE_INDENT,
				filled = true
			),

			// Stem.
			DrawCommand.Line(
				x1 = x,
				y1 = y - radius * 0.45f,
				x2 = x + radius * 0.08f,
				y2 = y - radius * 0.9f,
				color = STEM_COLOR
			),

			// Leaf.
			DrawCommand.Circle(
				cx = x + radius * 0.35f,
				cy = y - radius * 0.72f,
				radius = radius * 0.25f,
				color = LEAF_COLOR,
				filled = true
			),

			// Highlight.
			DrawCommand.Circle(
				cx = x - radius * 0.35f,
				cy = y - radius * 0.2f,
				radius = radius * 0.12f,
				color = APPLE_HIGHLIGHT,
				filled = true
			)
		)
	}
}
