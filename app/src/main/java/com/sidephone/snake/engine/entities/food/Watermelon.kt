package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Watermelon : FoodTypeInterface {
	const val WATERMELON_RIND: Int = 0xFF3F8F45.toInt()
	const val WATERMELON_RIND_DARK: Int = 0xFF276B35.toInt()
	const val WATERMELON_FLESH: Int = 0xFFF05A62.toInt()
	const val WATERMELON_FLESH_LIGHT: Int = 0xFFFF7478.toInt()
	const val WATERMELON_SEED: Int = 0xFF211C16.toInt()

	const val AMOUNT = 0.5f

	private var radius = Food.BASE_RADIUS

	override fun amount(): Float {
		return AMOUNT
	}

	override fun radius(): Float {
		return radius
	}

	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.75f + Math.random().toFloat() * 5f
	}

	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Outer rind.
			DrawCommand.Circle(
				cx = x - radius * 0.05f,
				cy = y,
				radius = radius * 0.72f,
				color = WATERMELON_RIND_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.2f,
				cy = y + radius * 0.05f,
				radius = radius * 0.67f,
				color = WATERMELON_RIND,
				filled = true
			),

			// Flesh
			DrawCommand.Circle(
				cx = x + radius * 0.18f,
				cy = y - radius * 0.03f,
				radius = radius * 0.58f,
				color = WATERMELON_FLESH,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.35f,
				cy = y + radius * 0.04f,
				radius = radius * 0.53f,
				color = WATERMELON_FLESH_LIGHT,
				filled = true
			),

			// Seeds
			DrawCommand.Circle(
				cx = x,
				cy = y - radius * 0.2f,
				radius = radius * 0.06f,
				color = WATERMELON_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.28f,
				cy = y - radius * 0.3f,
				radius = radius * 0.06f,
				color = WATERMELON_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.54f,
				cy = y - radius * 0.05f,
				radius = radius * 0.06f,
				color = WATERMELON_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.21f,
				cy = y + radius * 0.25f,
				radius = radius * 0.06f,
				color = WATERMELON_SEED,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.58f,
				cy = y + radius * 0.25f,
				radius = radius * 0.06f,
				color = WATERMELON_SEED,
				filled = true
			)
		)
	}
}
