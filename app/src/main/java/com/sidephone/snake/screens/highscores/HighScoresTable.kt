package com.sidephone.snake.screens.highscores

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.engine.HighScores

@Composable
fun HighScoresTable(scores: HighScores) {
	if (scores.isEmpty()) {
		Text(
			text = stringResource(R.string.high_scores_empty),
			style = typography.bodyMedium,
			color = colorScheme.onBackground
		)
	} else {
		scores.getAll().forEachIndexed { index, (name, score) ->
			Text(
				text = "${index + 1}. $name – $score",
				style = typography.bodyMedium,
				color = colorScheme.onBackground
			)
		}
	}
}
