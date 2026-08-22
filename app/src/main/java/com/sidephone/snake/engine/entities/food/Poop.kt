package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Poop : FoodTypeInterface {
	const val POOP_COLOR: Int = 0xFF8B5A2B.toInt()
	const val POOP_DARK: Int = 0xFF633D1D.toInt()
	const val POOP_HIGHLIGHT: Int = 0xFFB9783A.toInt()
	const val POOP_EYE: Int = 0xFF211C16.toInt()
	const val POOP_MOUTH: Int = 0xFF4A2818.toInt()

	const val AMOUNT = -5f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.6f + Math.random().toFloat() * 0.25f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Base.
			DrawCommand.Rect(
				left = x - radius * 0.5f,
				top = y + radius * 0.05f,
				right = x + radius * 0.5f,
				bottom = y + radius * 0.48f,
				color = POOP_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.5f,
				cy = y + radius * 0.27f,
				radius = radius * 0.25f,
				color = POOP_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.5f,
				cy = y + radius * 0.27f,
				radius = radius * 0.25f,
				color = POOP_DARK,
				filled = true
			),

			// Main mound.
			DrawCommand.Circle(
				cx = x,
				cy = y,
				radius = radius * 0.48f,
				color = POOP_COLOR,
				filled = true
			),

			// Upper mound.
			DrawCommand.Circle(
				cx = x - radius * 0.05f,
				cy = y - radius * 0.2f,
				radius = radius * 0.36f,
				color = POOP_COLOR,
				filled = true
			),

			// Short tip.
			DrawCommand.Circle(
				cx = x + radius * 0.03f,
				cy = y - radius * 0.42f,
				radius = radius * 0.22f,
				color = POOP_COLOR,
				filled = true
			),

			// Highlight.
			DrawCommand.Circle(
				cx = x - radius * 0.25f,
				cy = y - radius * 0.12f,
				radius = radius * 0.07f,
				color = POOP_HIGHLIGHT,
				filled = true
			),

			// Eyes.
			DrawCommand.Circle(
				cx = x - radius * 0.2f,
				cy = y + radius * 0.02f,
				radius = radius * 0.055f,
				color = POOP_EYE,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.2f,
				cy = y + radius * 0.02f,
				radius = radius * 0.055f,
				color = POOP_EYE,
				filled = true
			),

			// Smile.
			DrawCommand.Line(
				x1 = x - radius * 0.15f,
				y1 = y + radius * 0.17f,
				x2 = x,
				y2 = y + radius * 0.23f,
				color = POOP_MOUTH
			),

			DrawCommand.Line(
				x1 = x,
				y1 = y + radius * 0.23f,
				x2 = x + radius * 0.15f,
				y2 = y + radius * 0.17f,
				color = POOP_MOUTH
			)
		)
	}
}
