package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Pizza : FoodTypeInterface {
	const val PIZZA_CRUST: Int = 0xFFD28A3A.toInt()
	const val PIZZA_CRUST_DARK: Int = 0xFF9E5728.toInt()
	const val PIZZA_CHEESE: Int = 0xFFFFC928.toInt()
	const val PIZZA_CHEESE_HIGHLIGHT: Int = 0xFFFFE477.toInt()
	const val PIZZA_TOMATO: Int = 0xFFD94A38.toInt()
	const val PIZZA_PEPPERONI: Int = 0xFF9E3028.toInt()


	const val AMOUNT = 1.1f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.4f + Math.random().toFloat() * 0.3f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Crust.
			DrawCommand.Rect(
				left = x - radius * 0.72f,
				top = y - radius * 0.7f,
				right = x + radius * 0.72f,
				bottom = y - radius * 0.42f,
				color = PIZZA_CRUST_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.65f,
				top = y - radius * 0.68f,
				right = x + radius * 0.65f,
				bottom = y - radius * 0.48f,
				color = PIZZA_CRUST,
				filled = true
			),

			// Main triangular slice.
			DrawCommand.Line(
				x1 = x - radius * 0.62f,
				y1 = y - radius * 0.45f,
				x2 = x,
				y2 = y + radius * 0.85f,
				color = PIZZA_CHEESE
			),

			DrawCommand.Line(
				x1 = x + radius * 0.62f,
				y1 = y - radius * 0.45f,
				x2 = x,
				y2 = y + radius * 0.85f,
				color = PIZZA_CHEESE
			),

			// Rounded tip to fill the bottom of the slice.
			DrawCommand.Circle(
				cx = x,
				cy = y + radius * 0.5f,
				radius = radius * 0.21f,
				color = PIZZA_CHEESE,
				filled = true
			),

			// Fill the slice with horizontal strips.
			DrawCommand.Rect(
				left = x - radius * 0.5f,
				top = y - radius * 0.4f,
				right = x + radius * 0.5f,
				bottom = y - radius * 0.15f,
				color = PIZZA_CHEESE,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.4f,
				top = y - radius * 0.15f,
				right = x + radius * 0.4f,
				bottom = y + radius * 0.1f,
				color = PIZZA_CHEESE,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.3f,
				top = y + radius * 0.1f,
				right = x + radius * 0.3f,
				bottom = y + radius * 0.35f,
				color = PIZZA_CHEESE,
				filled = true
			),

			// Tomato sauce.
			DrawCommand.Rect(
				left = x - radius * 0.52f,
				top = y - radius * 0.45f,
				right = x + radius * 0.52f,
				bottom = y - radius * 0.38f,
				color = PIZZA_TOMATO,
				filled = true
			),

			// Pepperoni.
			DrawCommand.Circle(
				cx = x - radius * 0.3f,
				cy = y - radius * 0.22f,
				radius = radius * 0.13f,
				color = PIZZA_PEPPERONI,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.3f,
				cy = y - radius * 0.25f,
				radius = radius * 0.13f,
				color = PIZZA_PEPPERONI,
				filled = true
			),

			DrawCommand.Circle(
				cx = x,
				cy = y + radius * 0.15f,
				radius = radius * 0.12f,
				color = PIZZA_PEPPERONI,
				filled = true
			),

			// Cheese highlights.
			DrawCommand.Circle(
				cx = x - radius * 0.1f,
				cy = y - radius * 0.38f,
				radius = radius * 0.07f,
				color = PIZZA_CHEESE_HIGHLIGHT,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.15f,
				cy = y + radius * 0.35f,
				radius = radius * 0.06f,
				color = PIZZA_CHEESE_HIGHLIGHT,
				filled = true
			),
		)
	}
}
