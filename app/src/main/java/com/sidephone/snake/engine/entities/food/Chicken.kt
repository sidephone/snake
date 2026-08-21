package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.graphics.DrawCommand

object Chicken : FoodTypeInterface {
	const val CHICKEN_COLOR: Int = 0xFFC97832.toInt()
	const val CHICKEN_DARK: Int = 0xFF9A4E27.toInt()
	const val CHICKEN_HIGHLIGHT: Int = 0xFFE59A4B.toInt()
	const val BONE_COLOR: Int = 0xFFE8D2A5.toInt()
	const val BONE_DARK: Int = 0xFFB99D72.toInt()

	const val AMOUNT = 1.5f
	private val RADIUS = Food.BASE_RADIUS * 1.2f + Math.random().toFloat() * 0.25f


	override fun amount(): Float {
		return AMOUNT
	}


	override fun radius(): Float {
		return RADIUS
	}


	override fun draw(x: Float, y: Float): List<DrawCommand> {
		return listOf(
			// Main meaty part.
			DrawCommand.Circle(
				cx = x,
				cy = y - RADIUS * 0.05f,
				radius = RADIUS * 0.62f,
				color = CHICKEN_DARK,
				filled = true
			),

			// Golden inner meat.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.05f,
				cy = y - RADIUS * 0.1f,
				radius = RADIUS * 0.52f,
				color = CHICKEN_COLOR,
				filled = true
			),

			// Rounded upper part.
			DrawCommand.Circle(
				cx = x + RADIUS * 0.3f,
				cy = y - RADIUS * 0.25f,
				radius = RADIUS * 0.35f,
				color = CHICKEN_COLOR,
				filled = true
			),

			// Highlight.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.2f,
				cy = y - RADIUS * 0.3f,
				radius = RADIUS * 0.12f,
				color = CHICKEN_HIGHLIGHT,
				filled = true
			),

			// Bone.
			DrawCommand.Line(
				x1 = x - RADIUS * 0.1f,
				y1 = y + RADIUS * 0.35f,
				x2 = x - RADIUS * 0.1f,
				y2 = y + RADIUS * 0.9f,
				color = BONE_COLOR
			),

			// Bone end.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.1f,
				cy = y + RADIUS * 0.9f,
				radius = RADIUS * 0.18f,
				color = BONE_COLOR,
				filled = true
			),

			// Small darker bone tip.
			DrawCommand.Circle(
				cx = x - RADIUS * 0.1f,
				cy = y + RADIUS * 1.0f,
				radius = RADIUS * 0.08f,
				color = BONE_DARK,
				filled = true
			)
		)
	}

}
