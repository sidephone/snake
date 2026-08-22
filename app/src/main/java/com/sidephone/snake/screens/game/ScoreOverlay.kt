package com.sidephone.snake.screens.game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sidephone.snake.R
import com.sidephone.snake.engine.Gameplay
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun ScoreOverlay(textColor: Color, gameplay: Gameplay) {
	val score by gameplay.score.collectAsState()

	Text(
		text = stringResource(id = R.string.game_score, score),
		style = typography.bodyLarge,
		color = textColor,
		textAlign = TextAlign.Center,
		modifier = Modifier
			.padding(top = Dimens.HudPadding)
			.fillMaxWidth(),
	)
}
