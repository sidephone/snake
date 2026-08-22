package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object IceCream : FoodTypeInterface {
	const val ICE_CREAM_CONE: Int = 0xFFD99A52.toInt()
	const val ICE_CREAM_CONE_DARK: Int = 0xFFA96A32.toInt()
	const val ICE_CREAM_CREAM: Int = 0xFFFFE8C2.toInt()
	const val ICE_CREAM_PINK: Int = 0xFFF58A9A.toInt()
	const val ICE_CREAM_CHOCOLATE: Int = 0xFF8B5A3C.toInt()

	const val AMOUNT = 2f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.45f + Math.random().toFloat() * 0.3f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Cone body.
			DrawCommand.Rect(
				left = x - radius * 0.35f,
				top = y + radius * 0.02f,
				right = x + radius * 0.35f,
				bottom = y + radius * 0.65f,
				color = ICE_CREAM_CONE_DARK,
				filled = true
			),

			// Lower cone section.
			DrawCommand.Rect(
				left = x - radius * 0.22f,
				top = y + radius * 0.6f,
				right = x + radius * 0.22f,
				bottom = y + radius * 1.07f,
				color = ICE_CREAM_CONE,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.15f,
				top = y + radius * 1.02f,
				right = x + radius * 0.15f,
				bottom = y + radius * 1.38f,
				color = ICE_CREAM_CONE,
				filled = true
			),

			// Sloping sides.
			DrawCommand.Line(
				x1 = x - radius * 0.35f,
				y1 = y + radius * 0.02f,
				x2 = x - radius * 0.06f,
				y2 = y + radius * 1.4f,
				color = ICE_CREAM_CONE_DARK
			),

			DrawCommand.Line(
				x1 = x + radius * 0.35f,
				y1 = y + radius * 0.02f,
				x2 = x + radius * 0.06f,
				y2 = y + radius * 1.4f,
				color = ICE_CREAM_CONE_DARK
			),

			// Bottom scoop.
			DrawCommand.Circle(
				cx = x,
				cy = y + radius * 0.02f,
				radius = radius * 0.48f,
				color = ICE_CREAM_CREAM,
				filled = true
			),

			// Pink scoop.
			DrawCommand.Circle(
				cx = x - radius * 0.22f,
				cy = y - radius * 0.32f,
				radius = radius * 0.42f,
				color = ICE_CREAM_PINK,
				filled = true
			),

			// Top scoop.
			DrawCommand.Circle(
				cx = x + radius * 0.12f,
				cy = y - radius * 0.62f,
				radius = radius * 0.36f,
				color = ICE_CREAM_CREAM,
				filled = true
			),

			// Small chocolate topping.
			DrawCommand.Circle(
				cx = x + radius * 0.28f,
				cy = y - radius * 0.78f,
				radius = radius * 0.07f,
				color = ICE_CREAM_CHOCOLATE,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.05f,
				cy = y - radius * 0.9f,
				radius = radius * 0.06f,
				color = ICE_CREAM_CHOCOLATE,
				filled = true
			)
		)
	}
}
