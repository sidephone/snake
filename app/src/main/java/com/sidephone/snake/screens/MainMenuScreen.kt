package com.sidephone.snake.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.ui.components.MenuButton
import com.sidephone.snake.ui.components.MenuTitle
import com.sidephone.snake.ui.modifiers.gamepadClickableButton
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun MainMenuScreen(
	modifier: Modifier = Modifier,
	isGamePaused: Boolean = false,
	onNewGame: () -> Unit,
	onEndGame: () -> Unit,
	onSettings: () -> Unit,
	onExit: () -> Unit
) {
	val firstButtonFocusRequester = remember { FocusRequester() }

	Column(
		modifier = modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(Dimens.MainMenuButtonContainerPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Top,
	) {
		MenuTitle(stringResource(R.string.app_name))

		val hasRequestedInitialFocus = remember { androidx.compose.runtime.mutableStateOf(false) }

		MenuButton(
			onClick = onNewGame,
			modifier = Modifier
				.gamepadClickableButton(onNewGame)
				.focusRequester(firstButtonFocusRequester)
				.onGloballyPositioned {
					if (!hasRequestedInitialFocus.value) {
						hasRequestedInitialFocus.value = true
						firstButtonFocusRequester.requestFocus()
					}
				}
		) {
			Text(stringResource(
					if (isGamePaused) R.string.main_resume_game else R.string.main_new_game
			))
		}

		if (isGamePaused) {
			MenuButton(
				onClick = onEndGame,
				modifier = Modifier.gamepadClickableButton(onEndGame)
			) {
				Text(stringResource(R.string.main_end_game))
			}
		}

		if (!isGamePaused) {
			MenuButton(
				onClick = onSettings,
				modifier = Modifier.gamepadClickableButton(onSettings)
			) {
				Text(stringResource(R.string.main_settings))
			}
		}

		MenuButton(
			onClick = onExit,
			modifier = Modifier.gamepadClickableButton(onExit)
		) {
			Text(stringResource(R.string.main_exit))
		}
	}
}
