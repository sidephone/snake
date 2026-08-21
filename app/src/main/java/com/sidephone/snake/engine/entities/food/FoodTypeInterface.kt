package com.sidephone.snake.engine.entities.food

import com.sidephone.snake.engine.graphics.DrawCommand

interface FoodTypeInterface {
	fun amount(): Float
	fun draw(x: Float, y: Float): List<DrawCommand>
	fun radius(): Float
}
