package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Mushroom : FoodTypeInterface {
	const val MUSHROOM_CAP: Int = 0xFFE04B3F.toInt()
	const val MUSHROOM_CAP_DARK: Int = 0xFFB83232.toInt()
	const val MUSHROOM_STEM: Int = 0xFFE8D6B3.toInt()
	const val MUSHROOM_STEM_DARK: Int = 0xFFC2AA82.toInt()
	const val MUSHROOM_SPOT: Int = 0xFFFFE9C5.toInt()

	const val AMOUNT = 5f

	private var radius = Food.BASE_RADIUS


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return radius
	}


	override fun randomizeRadius() {
		radius = Food.BASE_RADIUS * 1.1f + Math.random().toFloat() * 0.25f
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Stem
			DrawCommand.Rect(
				left = x - radius * 0.28f,
				top = y,
				right = x + radius * 0.28f,
				bottom = y + radius * 1.2f,
				color = MUSHROOM_STEM_DARK,
				filled = true
			),

			DrawCommand.Rect(
				left = x - radius * 0.2f,
				top = y,
				right = x + radius * 0.2f,
				bottom = y + radius * 1.12f,
				color = MUSHROOM_STEM,
				filled = true
			),

			// Main cap - slightly smaller.
			DrawCommand.Circle(
				cx = x,
				cy = y - radius * 0.05f,
				radius = radius * 0.58f,
				color = MUSHROOM_CAP_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x,
				cy = y - radius * 0.12f,
				radius = radius * 0.51f,
				color = MUSHROOM_CAP,
				filled = true
			),

			// Left lobe.
			DrawCommand.Circle(
				cx = x - radius * 0.5f,
				cy = y - radius * 0.02f,
				radius = radius * 0.4f,
				color = MUSHROOM_CAP_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.5f,
				cy = y - radius * 0.08f,
				radius = radius * 0.34f,
				color = MUSHROOM_CAP,
				filled = true
			),

			// Right lobe.
			DrawCommand.Circle(
				cx = x + radius * 0.5f,
				cy = y - radius * 0.02f,
				radius = radius * 0.4f,
				color = MUSHROOM_CAP_DARK,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.5f,
				cy = y - radius * 0.08f,
				radius = radius * 0.34f,
				color = MUSHROOM_CAP,
				filled = true
			),

			// Spots.
			DrawCommand.Circle(
				cx = x - radius * 0.35f,
				cy = y - radius * 0.3f,
				radius = radius * 0.11f,
				color = MUSHROOM_SPOT,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.25f,
				cy = y - radius * 0.4f,
				radius = radius * 0.13f,
				color = MUSHROOM_SPOT,
				filled = true
			),

			DrawCommand.Circle(
				cx = x + radius * 0.48f,
				cy = y - radius * 0.02f,
				radius = radius * 0.09f,
				color = MUSHROOM_SPOT,
				filled = true
			),

			DrawCommand.Circle(
				cx = x - radius * 0.58f,
				cy = y - radius * 0.02f,
				radius = radius * 0.08f,
				color = MUSHROOM_SPOT,
				filled = true
			)
		)
	}
}
