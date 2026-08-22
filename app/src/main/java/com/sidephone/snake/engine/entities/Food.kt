package com.sidephone.snake.engine.entities

import com.sidephone.snake.engine.entities.food.Apple
import com.sidephone.snake.engine.entities.food.Burger
import com.sidephone.snake.engine.entities.food.IceCream
import com.sidephone.snake.engine.entities.food.Chicken
import com.sidephone.snake.engine.entities.food.Egg
import com.sidephone.snake.engine.entities.food.Fish
import com.sidephone.snake.engine.entities.food.FoodTypeInterface
import com.sidephone.snake.engine.entities.food.Mushroom
import com.sidephone.snake.engine.entities.food.Pineapple
import com.sidephone.snake.engine.entities.food.Pizza
import com.sidephone.snake.engine.entities.food.Poop
import com.sidephone.snake.engine.entities.food.Watermelon
import com.sidephone.snake.engine.graphics.DrawCommand

class Food {
	companion object {
		const val BASE_RADIUS = 15f
		const val DURATION_MIN = 5000L
		const val DURATION_MAX = 15000L
	}

	private val foodTypes = listOf(Apple, Burger, Chicken, Egg, Fish, IceCream, Mushroom, Pineapple, Pizza, Poop, Watermelon)

	private var amount = 0f
	private var foodType: FoodTypeInterface = Apple
	private var x = 0f
	private var y = 0f
	private var timeout = -1L


	fun amount(): Float { return amount }
	fun destroy() { amount = 0f }
	fun draw(): List<DrawCommand> { return foodType.draw(x, y) }
	fun exists(now: Long): Boolean { return amount != 0f && now < timeout }
	fun position(): Pair<Float, Float> { return Pair(x, y) }
	fun radius(): Float { return foodType.radius() }

	fun spawn(screenWidth: Float, screenHeight: Float, now: Long, keepUntilEaten: Boolean) {
		foodType = foodTypes.random()
		amount = foodType.amount()

		foodType.randomizeRadius()
		val radius = foodType.radius()

		val maxX = (screenWidth - radius * 4f).coerceAtLeast(0f)
		val maxY = (screenHeight - radius * 4f).coerceAtLeast(0f)
		x = radius * 2f + (Math.random().toFloat() * maxX)
		y = radius * 2f + (Math.random().toFloat() * maxY)

		timeout = if (keepUntilEaten)
			Long.MAX_VALUE
		else
			now + DURATION_MIN + (Math.random() * (DURATION_MAX - DURATION_MIN)).toLong()
	}
}
