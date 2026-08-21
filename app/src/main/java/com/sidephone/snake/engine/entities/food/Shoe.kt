package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Shoe : FoodTypeInterface {
	const val SHOE_CANVAS: Int = 0xFFD94A38.toInt()
	const val SHOE_CANVAS_DARK: Int = 0xFF9E3028.toInt()
	const val SHOE_WHITE: Int = 0xFFF3E8CF.toInt()
	const val SHOE_SOLE: Int = 0xFFE5D8BD.toInt()
	const val SHOE_SOLE_DARK: Int = 0xFFB9A98C.toInt()
	const val SHOE_LACE: Int = 0xFF211C16.toInt()

	const val AMOUNT = -5f

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
		return listOf(
			// Sole.
			DrawCommand.Rect(
				left = x - radius * 1.0f,
				top = y + radius * 0.35f,
				right = x + radius * 1.05f,
				bottom = y + radius * 0.6f,
				color = SHOE_SOLE_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.92f,
				top = y + radius * 0.28f,
				right = x + radius * 1.0f,
				bottom = y + radius * 0.5f,
				color = SHOE_SOLE,
				filled = true
			),

			// Main canvas body.
			DrawCommand.Rect(
				left = x - radius * 0.7f,
				top = y - radius * 0.35f,
				right = x + radius * 0.7f,
				bottom = y + radius * 0.35f,
				color = SHOE_CANVAS_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.62f,
				top = y - radius * 0.3f,
				right = x + radius * 0.65f,
				bottom = y + radius * 0.28f,
				color = SHOE_CANVAS,
				filled = true
			),

			// Raised heel at the back.
			DrawCommand.Rect(
				left = x - radius * 0.95f,
				top = y - radius * 0.05f,
				right = x - radius * 0.65f,
				bottom = y + radius * 0.42f,
				color = SHOE_CANVAS_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.88f,
				top = y - radius * 0.02f,
				right = x - radius * 0.68f,
				bottom = y + radius * 0.32f,
				color = SHOE_CANVAS,
				filled = true
			),

			// Ankle/collar.
			DrawCommand.Circle(
				cx = x - radius * 0.48f,
				cy = y - radius * 0.3f,
				radius = radius * 0.38f,
				color = SHOE_CANVAS_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.73f,
				top = y - radius * 0.3f,
				right = x - radius * 0.32f,
				bottom = y - radius * 0.05f,
				color = SHOE_CANVAS,
				filled = true
			),

			// White toe cap.
			DrawCommand.Circle(
				cx = x + radius * 0.78f,
				cy = y + radius * 0.08f,
				radius = radius * 0.38f,
				color = SHOE_WHITE,
				filled = true
			),

			// White side stripe.
			DrawCommand.Rect(
				left = x - radius * 0.05f,
				top = y - radius * 0.05f,
				right = x + radius * 0.72f,
				bottom = y + radius * 0.08f,
				color = SHOE_WHITE,
				filled = true
			),

			// Laces.
			DrawCommand.Line(
				x1 = x - radius * 0.48f,
				y1 = y - radius * 0.12f,
				x2 = x - radius * 0.05f,
				y2 = y - radius * 0.02f,
				color = SHOE_LACE
			),

			DrawCommand.Line(
				x1 = x - radius * 0.45f,
				y1 = y - radius * 0.25f,
				x2 = x - radius * 0.02f,
				y2 = y - radius * 0.15f,
				color = SHOE_LACE
			),

			DrawCommand.Line(
				x1 = x - radius * 0.4f,
				y1 = y - radius * 0.38f,
				x2 = x + radius * 0.03f,
				y2 = y - radius * 0.28f,
				color = SHOE_LACE
			),

			// Ankle patch.
			DrawCommand.Circle(
				cx = x - radius * 0.52f,
				cy = y - radius * 0.3f,
				radius = radius * 0.13f,
				color = SHOE_WHITE,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.52f,
				cy = y - radius * 0.3f,
				radius = radius * 0.055f,
				color = SHOE_CANVAS_DARK,
				filled = true
			)
		)
	}
}
