package com.sidephone.snake.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sidephone.snake.R
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.MenuButton
import com.sidephone.snake.util.gamepadClickableButton

@Composable
fun MainMenuScreen(
	modifier: Modifier = Modifier,
	isGamePaused: Boolean = false,
	onNewGame: () -> Unit,
	onEndGame: () -> Unit,
	onHighScores: () -> Unit,
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
		Text(
			text = stringResource(R.string.app_name),
			style = MaterialTheme.typography.headlineMedium,
			textAlign = TextAlign.Center,
			modifier = Modifier.padding(
				top = Dimens.MainMenuTitlePaddingTop,
				bottom = Dimens.MainMenuTitlePaddingBottom
			),
			color = MaterialTheme.colorScheme.onBackground
		)

		val buttonModifiers = Modifier
			.fillMaxWidth()
			.padding(
				bottom = Dimens.MainMenuButtonPaddingBottom,
				start = Dimens.MainMenuButtonPaddingHorizontal,
				end = Dimens.MainMenuButtonPaddingHorizontal
			)

		val hasRequestedInitialFocus = remember { androidx.compose.runtime.mutableStateOf(false) }

		MenuButton(
			onClick = onNewGame,
			modifier = buttonModifiers
				.focusRequester(firstButtonFocusRequester)
				.onGloballyPositioned {
					if (!hasRequestedInitialFocus.value) {
						hasRequestedInitialFocus.value = true
						firstButtonFocusRequester.requestFocus()
					}
				}
				.gamepadClickableButton(onNewGame)
		) {
			Text(stringResource(
					if (isGamePaused) R.string.main_resume_game else R.string.main_new_game
			))
		}

		if (isGamePaused) {
			MenuButton(
				onClick = onEndGame,
				modifier = buttonModifiers.gamepadClickableButton(onEndGame)
			) {
				Text(stringResource(R.string.main_end_game))
			}
		}

		MenuButton(
			onClick = onHighScores,
			modifier = buttonModifiers.gamepadClickableButton(onHighScores)
		) {
			Text(stringResource(R.string.main_high_scores))
		}

		MenuButton(
			onClick = onExit,
			modifier = buttonModifiers.gamepadClickableButton(onExit)
		) {
			Text(stringResource(R.string.main_exit))
		}
	}
}
