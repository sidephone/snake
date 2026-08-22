package com.sidephone.snake.settings

object GameplaySettings {
	const val TARGET_FPS = 30 // Rendering frames per second. Use multiples of the device screen refresh rate for better performance
	const val TARGET_IPS = 15 // Advance game logic N iterations per second. Indirectly affects the FPS
	const val GAME_SPEED = 50 // %
	const val FOOD_STAYS_UNTIL_EATEN = false // If true, food will stay on the screen until eaten. If false, food will disappear after a while
}
