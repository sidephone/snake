package com.sidephone.snake.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.components.BackToMainButton
import com.sidephone.snake.ui.components.MenuTitle
import com.sidephone.snake.ui.theme.Dimens


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
		MenuTitle(stringResource(R.string.main_settings))
		FoodStaysOption(settings)
		BackToMainButton(onBack)
	}
}
