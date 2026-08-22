package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Fish : FoodTypeInterface {
	const val FISH_COLOR: Int = 0xFF5DADE2.toInt()
	const val FISH_DARK: Int = 0xFF2874A6.toInt()
	const val FISH_HIGHLIGHT: Int = 0xFF85C1E9.toInt()
	const val FISH_EYE: Int = 0xFF211C16.toInt()

	const val AMOUNT = 1.4f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.35f + Math.random().toFloat() * 0.25f
	}

	override fun draw(x: Float, y: Float): List<DrawCommand> {
		val bodyColor = FISH_COLOR
		val darkColor = FISH_DARK

		return listOf(
			// Main long body.
			DrawCommand.Rect(
				left = x - radius * 0.65f,
				top = y - radius * 0.38f,
				right = x + radius * 0.65f,
				bottom = y + radius * 0.38f,
				color = bodyColor,
				filled = true
			),

			// Rounded middle.
			DrawCommand.Circle(
				cx = x,
				cy = y,
				radius = radius * 0.48f,
				color = bodyColor,
				filled = true
			),

			// Narrowing tail end.
			DrawCommand.Rect(
				left = x - radius * 0.85f,
				top = y - radius * 0.25f,
				right = x - radius * 0.55f,
				bottom = y + radius * 0.25f,
				color = bodyColor,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius,
				top = y - radius * 0.12f,
				right = x - radius * 0.8f,
				bottom = y + radius * 0.12f,
				color = bodyColor,
				filled = true
			),

			// Narrowing head end.
			DrawCommand.Rect(
				left = x + radius * 0.55f,
				top = y - radius * 0.25f,
				right = x + radius * 0.85f,
				bottom = y + radius * 0.25f,
				color = bodyColor,
				filled = true
			),

			DrawCommand.Rect(
				left = x + radius * 0.8f,
				top = y - radius * 0.12f,
				right = x + radius,
				bottom = y + radius * 0.12f,
				color = bodyColor,
				filled = true
			),

			// Tail fin.
			DrawCommand.Rect(
				left = x - radius,
				top = y - radius * 0.48f,
				right = x - radius * 0.82f,
				bottom = y - radius * 0.1f,
				color = darkColor,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius,
				top = y + radius * 0.1f,
				right = x - radius * 0.82f,
				bottom = y + radius * 0.48f,
				color = darkColor,
				filled = true
			),

			// Stronger tail outline.
			DrawCommand.Line(
				x1 = x - radius * 0.65f,
				y1 = y,
				x2 = x - radius,
				y2 = y - radius * 0.48f,
				color = darkColor
			),

			DrawCommand.Line(
				x1 = x - radius * 0.65f,
				y1 = y,
				x2 = x - radius,
				y2 = y + radius * 0.48f,
				color = darkColor
			),

			// Tail long lines.
			DrawCommand.Line(
				x1 = x - radius * 0.95f,
				y1 = y - radius * 0.35f,
				x2 = x - radius * 1.3f,
				y2 = y - radius * 0.65f,
				color = darkColor
			),

			DrawCommand.Line(
				x1 = x - radius * 0.95f,
				y1 = y + radius * 0.35f,
				x2 = x - radius * 1.3f,
				y2 = y + radius * 0.65f,
				color = darkColor
			),

			// Top fin.
			DrawCommand.Rect(
				left = x - radius * 0.15f,
				top = y - radius * 0.65f,
				right = x + radius * 0.15f,
				bottom = y - radius * 0.35f,
				color = darkColor,
				filled = true
			),

			// Bottom fin.
			DrawCommand.Rect(
				left = x - radius * 0.15f,
				top = y + radius * 0.35f,
				right = x + radius * 0.15f,
				bottom = y + radius * 0.65f,
				color = darkColor,
				filled = true
			),

			// Eye.
			DrawCommand.Circle(
				cx = x + radius * 0.62f,
				cy = y - radius * 0.15f,
				radius = radius * 0.1f,
				color = FISH_EYE,
				filled = true
			),

			// Eye highlight.
			DrawCommand.Circle(
				cx = x + radius * 0.65f,
				cy = y - radius * 0.18f,
				radius = radius * 0.035f,
				color = FISH_HIGHLIGHT,
				filled = true
			)
		)
	}
}
