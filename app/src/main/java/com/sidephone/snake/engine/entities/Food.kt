package com.sidephone.snake.engine.entities

import com.sidephone.snake.engine.entities.food.Apple
import com.sidephone.snake.engine.entities.food.Chicken
import com.sidephone.snake.engine.entities.food.Egg
import com.sidephone.snake.engine.entities.food.FoodTypeInterface
import com.sidephone.snake.engine.entities.food.Pineapple
import com.sidephone.snake.engine.graphics.DrawCommand

class Food {
	companion object {
		const val BASE_RADIUS = 15f
	}

	private var amount = 0f
	private var x = 0f
	private var y = 0f
	private var foodType: FoodTypeInterface = Apple

	private val foodTypes = listOf(Apple, Chicken, Egg, Pineapple)

	fun amount(): Float { return amount }
	fun destroy() { amount = 0f }
	fun draw(): List<DrawCommand> { return foodType.draw(x, y) }
	fun exists(): Boolean { return amount > 0f }
	fun position(): Pair<Float, Float> { return Pair(x, y) }
	fun radius(): Float { return foodType.radius() }


	fun spawn(screenWidth: Float, screenHeight: Float) {
		foodType = foodTypes.random()
		amount = foodType.amount()

		val radius = foodType.radius()

		val maxX = (screenWidth - radius * 4f).coerceAtLeast(0f)
		val maxY = (screenHeight - radius * 4f).coerceAtLeast(0f)
		x = radius * 2f + (Math.random().toFloat() * maxX)
		y = radius * 2f + (Math.random().toFloat() * maxY)
	}
}
