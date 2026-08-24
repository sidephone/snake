package com.sidephone.snake.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.MenuButton
import com.sidephone.snake.util.gamepadClickableButton

@Composable
fun SelectDifficultyScreen(
	modifier: Modifier = Modifier,
	settings: Settings,
	onSelectDifficulty: (Settings.Difficulty) -> Unit
) {
	val easyFocusRequester = remember { FocusRequester() }
	val mediumFocusRequester = remember { FocusRequester() }
	val hardFocusRequester = remember { FocusRequester() }

	var difficulty by remember { mutableStateOf(settings.getDifficulty()) }

	Column(
		modifier = modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(Dimens.MainMenuButtonContainerPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Top,
	) {
		Text(
			text = stringResource(R.string.difficulty_choose),
			style = MaterialTheme.typography.headlineLarge,
			textAlign = TextAlign.Center,
			modifier = Modifier.padding(
				top = Dimens.MainMenuTitlePaddingTop,
				bottom = Dimens.MainMenuTitlePaddingBottom
			),
			color = MaterialTheme.colorScheme.onBackground
		)

		MenuButton(
			onClick = {
				difficulty = Settings.Difficulty.EASY
				onSelectDifficulty(Settings.Difficulty.EASY)
			},
			modifier = Modifier
				.gamepadClickableButton {
					difficulty = Settings.Difficulty.EASY
					onSelectDifficulty(Settings.Difficulty.EASY)
				}
				.focusRequester(easyFocusRequester)
				.onGloballyPositioned {
					if (difficulty == Settings.Difficulty.EASY) {
						easyFocusRequester.requestFocus()
					} else {
						Log.d("SelectDifficultyScreen", "$difficulty != EASY")
					}
				}
		) {
			Text(stringResource(R.string.difficulty_easy))
		}

		MenuButton(
			onClick = {
				difficulty = Settings.Difficulty.MEDIUM
				onSelectDifficulty(Settings.Difficulty.MEDIUM)
			},
			modifier = Modifier
				.gamepadClickableButton {
					difficulty = Settings.Difficulty.MEDIUM
					onSelectDifficulty(Settings.Difficulty.MEDIUM)
				}
				.focusRequester(mediumFocusRequester)
				.onGloballyPositioned {
					if (difficulty == Settings.Difficulty.MEDIUM) {
						mediumFocusRequester.requestFocus()
					} else {
						Log.d("SelectDifficultyScreen", "$difficulty != MEDIUM")
					}
				}
		) {
			Text(stringResource(R.string.difficulty_medium))
		}

		MenuButton(
			onClick = {
				difficulty = Settings.Difficulty.HARD
				onSelectDifficulty(Settings.Difficulty.HARD)
			},
			modifier = Modifier
				.gamepadClickableButton {
					difficulty = Settings.Difficulty.HARD
					onSelectDifficulty(Settings.Difficulty.HARD)
				}
				.focusRequester(hardFocusRequester)
				.onGloballyPositioned {
					if (difficulty == Settings.Difficulty.HARD) {
						hardFocusRequester.requestFocus()
					} else {
						Log.d("SelectDifficultyScreen", "$difficulty != HARD")
					}
				}
		) {
			Text(stringResource(R.string.difficulty_hard))
		}
	}
}
