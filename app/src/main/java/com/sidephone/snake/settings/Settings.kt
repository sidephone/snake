package com.sidephone.snake.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sidephone.snake.engine.HighScores

class Settings(context: android.content.Context) {
	companion object {
		private const val PREFS_NAME = "SnakeSettings"

		private const val FOOD_STAYS_UNTIL_EATEN_KEY = "food_stays_until_eaten"
		private const val GAME_SPEED_KEY = "game_speed"
		private const val HIGH_SCORES_NAME_PREFIX = "high_scores_name_"
		private const val HIGH_SCORES_SCORE_PREFIX = "high_scores_score_"

		const val GAME_SPEED_MIN = 10
		const val GAME_SPEED_MAX = 100
	}

	object Gameplay {
		const val TARGET_FPS = 30 // Rendering frames per second. Use multiples of the device screen refresh rate for better performance
		const val TARGET_IPS = 15 // Advance game logic N iterations per second. Indirectly affects the FPS
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)


	/**
	 * Saves the high scores to the shared preferences.
	 */
	fun saveHighScores(scores: HighScores) {
		sharedPreferences.edit {
			scores.getAll().forEachIndexed { index, (name, score) ->
				putString("$HIGH_SCORES_NAME_PREFIX$index", name)
				putInt("$HIGH_SCORES_SCORE_PREFIX$index", score)
			}
		}
	}


	/**
	 * Loads the high scores from the shared preferences.
	 */
	fun loadHighScores(highScores: HighScores) {
		highScores.clear()
		for (i in 0 until HighScores.MAX) {
			val name = sharedPreferences.getString("$HIGH_SCORES_NAME_PREFIX$i", null)
			val score = sharedPreferences.getInt("$HIGH_SCORES_SCORE_PREFIX$i", -1)
			if (name != null && score >= 0) {
				highScores.addScore(name, score)
			}
		}
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
	 * Returns the game speed as a percentage (0-100).
	 */
	fun gameSpeed(): Int {
		return sharedPreferences.getInt(GAME_SPEED_KEY, 50)
	}


	/**
	 * Sets the game speed as a percentage. Clamped to [10, 100] to guard against
	 * invalid values reaching the game engine (e.g. from a malformed UI state).
	 */
	fun setGameSpeed(speed: Int) {
		val clampedSpeed = speed.coerceIn(GAME_SPEED_MIN, GAME_SPEED_MAX)
		sharedPreferences.edit {
			putInt(GAME_SPEED_KEY, clampedSpeed)
		}
	}
}
