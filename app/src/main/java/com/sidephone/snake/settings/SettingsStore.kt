package com.sidephone.snake.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sidephone.snake.engine.HighScores

class SettingsStore(context: android.content.Context) {
	companion object {
		private const val PREFS_NAME = "SnakeSettings"
		private const val HIGH_SCORES_NAME_PREFIX = "high_scores_name_"
		private const val HIGH_SCORES_SCORE_PREFIX = "high_scores_score_"
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)

	fun saveHighScores(scores: HighScores) {
		sharedPreferences.edit {
			scores.getAll().forEachIndexed { index, (name, score) ->
				putString("$HIGH_SCORES_NAME_PREFIX$index", name)
				putInt("$HIGH_SCORES_SCORE_PREFIX$index", score)
			}
		}
	}

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
}
