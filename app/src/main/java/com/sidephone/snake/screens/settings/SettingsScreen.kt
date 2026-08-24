package com.sidephone.snake.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.BackToMainButton


@Composable
fun SettingsScreen(settings: Settings, onBack: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(Dimens.MainMenuButtonContainerPadding),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Top
	) {
		Text(
			text = stringResource(R.string.main_settings),
			style = typography.headlineLarge,
			modifier = Modifier.padding(
				top = Dimens.MainMenuTitlePaddingTop,
				bottom = Dimens.MainMenuTitlePaddingBottom
			),
			color = colorScheme.onBackground
		)

		FoodStaysOption(settings)
		GameSpeedDropdown(
        initialSpeed = settings.gameSpeed(),
        onSpeedSelected = { speed -> settings.setGameSpeed(speed) }
    )
		BackToMainButton(onBack)
	}
}
