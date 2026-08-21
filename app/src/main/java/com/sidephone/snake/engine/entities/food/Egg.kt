package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Egg : FoodTypeInterface {
	const val EGG_WHITE: Int = 0xFFF3E8CF.toInt()
	const val EGG_EDGE: Int = 0xFFD8C7A8.toInt()
	const val EGG_YOLK: Int = 0xFFFFC928.toInt()
	const val EGG_YOLK_HIGHLIGHT: Int = 0xFFFFE477.toInt()

	const val AMOUNT = 1f
	private val RADIUS = Food.BASE_RADIUS * 1.75f + Math.random().toFloat() * 0.75f


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return RADIUS
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Shell silhouette.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.12f,
				cy = y,
				radius = RADIUS * 0.62f,
				color = EGG_EDGE,
				filled = true
			),

			// Main white interior.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.08f,
				cy = y - RADIUS * 0.05f,
				radius = RADIUS * 0.55f,
				color = EGG_WHITE,
				filled = true
			),

			// Slightly wider bottom to give it an egg-like silhouette.
			DrawCommand.Circle(
				cx = x + RADIUS * 0.08f,
				cy = y + RADIUS * 0.25f,
				radius = RADIUS * 0.45f,
				color = EGG_WHITE,
				filled = true
			),

			// Yolk.
			DrawCommand.Circle(
				cx = x + RADIUS * 0.05f,
				cy = y + RADIUS * 0.08f,
				radius = RADIUS * 0.28f,
				color = EGG_YOLK,
				filled = true
			),

			// Yolk highlight.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.04f,
				cy = y - RADIUS * 0.02f,
				radius = RADIUS * 0.08f,
				color = EGG_YOLK_HIGHLIGHT,
				filled = true
			)
		)
	}
}
