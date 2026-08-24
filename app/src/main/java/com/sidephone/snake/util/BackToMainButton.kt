package com.sidephone.snake.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun BackToMainButton(onBack: () -> Unit) {
	MenuButton(
		onClick = onBack,
		modifier = Modifier
			.padding(top = Dimens.MainMenuTitlePaddingBottom)
			.gamepadClickableButton(onBack)
	) {
		Text(stringResource(R.string.main_back))
	}
}
