package com.sidephone.snake.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.components.MenuButton
import com.sidephone.snake.ui.components.MenuTitle
import com.sidephone.snake.ui.modifiers.gamepadClickableButton
import com.sidephone.snake.ui.theme.Dimens

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
		MenuTitle(stringResource(R.string.difficulty_choose))

		MenuButton(
			text = R.string.difficulty_easy,
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
					}
				}
		)

		MenuButton(
			text = R.string.difficulty_medium,
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
					}
				}
		)

		MenuButton(
			text = R.string.difficulty_hard,
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
					}
				}
		)
	}
}
