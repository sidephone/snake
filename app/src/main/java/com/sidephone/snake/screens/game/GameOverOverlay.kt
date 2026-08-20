package com.sidephone.snake.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sidephone.snake.R
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun GameOverOverlay() {
	val hudTextColor = Color.White
	val hudGameOverMaskColor = Color.Black.copy(alpha = 0.4f)

	Column(
		modifier = Modifier
			.background(hudGameOverMaskColor)
			.fillMaxSize()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			Column(
				modifier = Modifier.align(Alignment.Center).fillMaxWidth()
			) {
				Text(
					text = stringResource(R.string.game_over),
					color = hudTextColor,
					modifier = Modifier.padding(bottom = Dimens.MainMenuTitlePaddingBottom).fillMaxWidth(),
					fontSize = MaterialTheme.typography.headlineLarge.fontSize,
					textAlign = TextAlign.Center
				)

				Text(
					text = stringResource(R.string.game_over_subtitle),
					color = hudTextColor,
					modifier = Modifier.fillMaxWidth(),
					fontSize = MaterialTheme.typography.bodyLarge.fontSize,
					textAlign = TextAlign.Center
				)
			}
		}
	}
}
