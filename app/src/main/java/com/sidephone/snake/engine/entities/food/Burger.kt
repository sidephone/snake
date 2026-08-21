package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Burger : FoodTypeInterface {
	const val BURGER_BUN: Int = 0xFFD9903D.toInt()
	const val BURGER_BUN_DARK: Int = 0xFF9E5A28.toInt()
	const val BURGER_CHEESE: Int = 0xFFFFC928.toInt()
	const val BURGER_PATTY: Int = 0xFF713B28.toInt()
	const val BURGER_LETTUCE: Int = 0xFF69A84F.toInt()
	const val BURGER_TOMATO: Int = 0xFFD94A38.toInt()
	const val BURGER_SEED: Int = 0xFFFFE1A3.toInt()


	const val AMOUNT = 2.8f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.6f + Math.random().toFloat() * 0.5f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Rounded bottom bun edges.
			DrawCommand.Circle(
				cx = x - radius * 0.48f,
				cy = y + radius * 0.4f,
				radius = radius * 0.25f,
				color = BURGER_BUN,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.48f,
				cy = y + radius * 0.4f,
				radius = radius * 0.25f,
				color = BURGER_BUN,
				filled = true
			),

			// Bottom bun.
			DrawCommand.Rect(
				left = x - radius * 0.68f,
				top = y + radius * 0.35f,
				right = x + radius * 0.68f,
				bottom = y + radius * 0.65f,
				color = BURGER_BUN_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.6f,
				top = y + radius * 0.3f,
				right = x + radius * 0.6f,
				bottom = y + radius * 0.55f,
				color = BURGER_BUN,
				filled = true
			),

			// Top bun base.
			DrawCommand.Rect(
				left = x - radius * 0.62f,
				top = y - radius * 0.48f,
				right = x + radius * 0.62f,
				bottom = y - radius * 0.32f,
				color = BURGER_BUN_DARK,
				filled = true
			),

			// Top bun dome.
			DrawCommand.Circle(
				cx = x,
				cy = y - radius * 0.42f,
				radius = radius * 0.55f,
				color = BURGER_BUN,
				filled = true
			),

			// Cover only the lower part of the dome to create a flat bottom.
			DrawCommand.Rect(
				left = x - radius * 0.55f,
				top = y - radius * 0.42f,
				right = x + radius * 0.55f,
				bottom = y - radius * 0.3f,
				color = BURGER_BUN,
				filled = true
			),

			// Seeds, drawn last so they remain visible.
			DrawCommand.Circle(
				cx = x - radius * 0.3f,
				cy = y - radius * 0.55f,
				radius = radius * 0.045f,
				color = BURGER_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.08f,
				cy = y - radius * 0.65f,
				radius = radius * 0.045f,
				color = BURGER_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.18f,
				cy = y - radius * 0.6f,
				radius = radius * 0.045f,
				color = BURGER_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.38f,
				cy = y - radius * 0.5f,
				radius = radius * 0.045f,
				color = BURGER_SEED,
				filled = true
			),
			// Patty.
			DrawCommand.Rect(
				left = x - radius * 0.72f,
				top = y + radius * 0.08f,
				right = x + radius * 0.72f,
				bottom = y + radius * 0.32f,
				color = BURGER_PATTY,
				filled = true
			),

			// Cheese.
			DrawCommand.Rect(
				left = x - radius * 0.68f,
				top = y - radius * 0.05f,
				right = x + radius * 0.68f,
				bottom = y + radius * 0.1f,
				color = BURGER_CHEESE,
				filled = true
			),

			// Tomato.
			DrawCommand.Rect(
				left = x - radius * 0.63f,
				top = y - radius * 0.2f,
				right = x + radius * 0.63f,
				bottom = y - radius * 0.05f,
				color = BURGER_TOMATO,
				filled = true
			),

			// Lettuce.
			DrawCommand.Rect(
				left = x - radius * 0.7f,
				top = y - radius * 0.34f,
				right = x + radius * 0.7f,
				bottom = y - radius * 0.19f,
				color = BURGER_LETTUCE,
				filled = true
			),
		)
	}
}
