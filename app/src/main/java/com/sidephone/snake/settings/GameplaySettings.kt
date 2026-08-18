package com.sidephone.snake.settings

object GameplaySettings {
	const val TARGET_FPS = 60 // Rendering frames per second. Use multiples of the device screen refresh rate for better performance
	const val TARGET_IPS = 15 // Advance game logic N iterations per second. Indirectly affects the FPS
	const val GAME_SPEED = 50 // %
}
