package com.sidephone.snake.screens.game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.engine.Gameplay
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun ScoreOverlay(textColor: Color, gameplay: Gameplay) {
	val score by gameplay.score.collectAsState()
	Text(
		text = stringResource(id = R.string.game_score, score),
		style = MaterialTheme.typography.labelMedium,
		color = textColor,
		textAlign = TextAlign.Center,
		modifier = Modifier
			.padding(2.dp)
			.fillMaxWidth(),
	)
}
