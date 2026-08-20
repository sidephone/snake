package com.sidephone.snake.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.engine.HighScores
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun HighScoresScreen(scores: HighScores) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(Dimens.MainMenuButtonContainerPadding),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = stringResource(R.string.high_scores_title),
			style = typography.headlineMedium,
			modifier = Modifier.padding(
				top = Dimens.MainMenuTitlePaddingTop,
				bottom = Dimens.MainMenuTitlePaddingBottom
			)
		)

		if (scores.isEmpty()) {
			Text(text = stringResource(R.string.high_scores_empty), style = typography.bodyMedium)
		} else {
			scores.getAll().forEachIndexed { index, (name, score) ->
				Text(text = "${index + 1}. $name – $score", style = typography.bodyMedium)
			}
		}
	}
}
