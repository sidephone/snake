package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Cake : FoodTypeInterface {
	const val CAKE_COLOR: Int = 0xFF9B5A3C.toInt()
	const val CAKE_DARK: Int = 0xFF70402E.toInt()
	const val CAKE_CREAM: Int = 0xFFFFE4B5.toInt()
	const val CAKE_FROSTING: Int = 0xFFFF8FA3.toInt()

	const val AMOUNT = 2f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.75f + Math.random().toFloat() * 0.3f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Bottom cake layer.
			DrawCommand.Rect(
				left = x - radius * 0.75f,
				top = y + radius * 0.05f,
				right = x + radius * 0.75f,
				bottom = y + radius * 0.65f,
				color = CAKE_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.65f,
				top = y + radius * 0.02f,
				right = x + radius * 0.65f,
				bottom = y + radius * 0.5f,
				color = CAKE_COLOR,
				filled = true
			),

			// Cream between layers.
			DrawCommand.Rect(
				left = x - radius * 0.7f,
				top = y - radius * 0.08f,
				right = x + radius * 0.7f,
				bottom = y + radius * 0.08f,
				color = CAKE_CREAM,
				filled = true
			),

			// Narrower second cake layer.
			DrawCommand.Rect(
				left = x - radius * 0.52f,
				top = y - radius * 0.42f,
				right = x + radius * 0.52f,
				bottom = y - radius * 0.08f,
				color = CAKE_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.44f,
				top = y - radius * 0.4f,
				right = x + radius * 0.44f,
				bottom = y - radius * 0.14f,
				color = CAKE_COLOR,
				filled = true
			),

			// Frosting on top of the second layer.
			DrawCommand.Rect(
				left = x - radius * 0.5f,
				top = y - radius * 0.52f,
				right = x + radius * 0.5f,
				bottom = y - radius * 0.38f,
				color = CAKE_FROSTING,
				filled = true
			),

			// Small frosting drips.
			DrawCommand.Circle(
				cx = x - radius * 0.32f,
				cy = y - radius * 0.3f,
				radius = radius * 0.1f,
				color = CAKE_FROSTING,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.32f,
				cy = y - radius * 0.3f,
				radius = radius * 0.1f,
				color = CAKE_FROSTING,
				filled = true
			),

			// Third, narrower cake layer.
			DrawCommand.Rect(
				left = x - radius * 0.4f,
				top = y - radius * 0.78f,
				right = x + radius * 0.4f,
				bottom = y - radius * 0.5f,
				color = CAKE_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.34f,
				top = y - radius * 0.76f,
				right = x + radius * 0.34f,
				bottom = y - radius * 0.54f,
				color = CAKE_COLOR,
				filled = true
			),

			// Frosting on the third layer.
			DrawCommand.Rect(
				left = x - radius * 0.38f,
				top = y - radius * 0.84f,
				right = x + radius * 0.38f,
				bottom = y - radius * 0.74f,
				color = CAKE_FROSTING,
				filled = true
			),
		)
	}
}
