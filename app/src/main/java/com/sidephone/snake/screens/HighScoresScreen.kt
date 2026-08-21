package com.sidephone.snake.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.engine.HighScores
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.MenuButton
import com.sidephone.snake.util.gamepadClickableButton

@Composable
fun HighScoresScreen(scores: HighScores, onBack: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(Dimens.MainMenuButtonContainerPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Top
	) {
		Text(
			text = stringResource(R.string.high_scores_title),
			style = typography.headlineMedium,
			modifier = Modifier.padding(
				top = Dimens.MainMenuTitlePaddingTop,
				bottom = Dimens.MainMenuTitlePaddingBottom
			),
			color = colorScheme.onBackground
		)

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

		MenuButton(
			onClick = onBack,
			modifier = Modifier.fillMaxWidth()
			.padding(
				top = Dimens.MainMenuTitlePaddingBottom,
				bottom = Dimens.MainMenuButtonPaddingBottom,
				start = Dimens.MainMenuButtonPaddingHorizontal,
				end = Dimens.MainMenuButtonPaddingHorizontal
			)
			.gamepadClickableButton(onBack)
		) {
			Text(stringResource(R.string.main_back))
		}
	}
}
