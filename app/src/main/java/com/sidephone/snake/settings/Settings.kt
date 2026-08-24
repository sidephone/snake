package com.sidephone.snake.settings

import android.content.SharedPreferences
import androidx.core.content.edit

class Settings(context: android.content.Context) {
	companion object {
		private const val PREFS_NAME = "SnakeSettings"

		private const val FOOD_STAYS_UNTIL_EATEN_KEY = "food_stays_until_eaten"
		private const val GAME_SPEED_KEY = "game_speed"
		private const val HIGH_SCORE_KEY = "high_score"

		const val GAME_SPEED_MIN = 10
		const val GAME_SPEED_MAX = 100
	}

	enum class Difficulty(val speed: Int) {
		EASY(30),
		MEDIUM(50),
		HARD(100);

		companion object {
			/**
			 * Returns the Difficulty whose defined speed is closest to the given value.
			 */
			fun fromSpeed(speed: Int): Difficulty {
				return entries.minByOrNull { kotlin.math.abs(it.speed - speed) } ?: MEDIUM
			}
		}
	}

	object Gameplay {
		const val TARGET_FPS = 30 // Rendering frames per second. Use multiples of the device screen refresh rate for better performance
		const val TARGET_IPS = 15 // Advance game logic N iterations per second. Indirectly affects the FPS
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)


	/**
	 * Saves the new score if it is greater than the last one. Returns true if an update happened.
	 */
	fun updateHighScoreIfNeeded(newScore: Int): Boolean {
		if (newScore > sharedPreferences.getInt(HIGH_SCORE_KEY, Int.MIN_VALUE)) {
			sharedPreferences.edit { putInt(HIGH_SCORE_KEY, newScore) }
			return true
		}

		return false
	}


	/**
	 * Loads the current high score value
	 */
	fun getHighScore(): Int {
		return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
	}


	/**
	 * Determines whether the food stays on the screen until the snake eats it, or it respawns
	 * on a new location after a certain time.
	 */
	fun foodStaysUntilEaten(): Boolean {
		return sharedPreferences.getBoolean(FOOD_STAYS_UNTIL_EATEN_KEY, false)
	}


	/**
	 * Sets whether the food stays on the screen until the snake eats it.
	 */
	fun setFoodStaysUntilEaten(staysUntilEaten: Boolean) {
		sharedPreferences.edit {
			putBoolean(FOOD_STAYS_UNTIL_EATEN_KEY, staysUntilEaten)
		}
	}


	/**
	 * Returns the game speed as a percentage between GAME_SPEED_MIN and GAME_SPEED_MAX
	 */
	fun gameSpeed(): Int {
		return sharedPreferences.getInt(GAME_SPEED_KEY, Difficulty.MEDIUM.speed)
	}


	/**
	 * Returns the game speed as a Difficulty enum value. The speed is clamped to the closest defined
	 * Difficulty if it doesn't match any exactly.
	 */
	fun getDifficulty(): Difficulty {
		val speed = gameSpeed()
		return Difficulty.fromSpeed(speed)
	}


	/**
	 * Sets the game speed as Difficulty enum value. For safety, the speed is clamped to
	 * [GAME_SPEED_MIN, GAME_SPEED_MAX].
	 */
	fun setDifficulty(difficulty: Difficulty) {
		val clampedSpeed = difficulty.speed.coerceIn(GAME_SPEED_MIN, GAME_SPEED_MAX)
		sharedPreferences.edit { putInt(GAME_SPEED_KEY, clampedSpeed) }
	}
}
