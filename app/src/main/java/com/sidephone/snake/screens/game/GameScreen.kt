package com.sidephone.snake.screens.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.sidephone.snake.engine.Gameplay


@Composable
fun GameScreen(gameplay: Gameplay) {
	val menuBackground = MaterialTheme.colorScheme.background.toArgb()

	val isGameOver by gameplay.isGameOver.collectAsState()

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context -> GameSurfaceView(context, gameplay, menuBackground) },
	)

	if (isGameOver) {
		GameOverOverlay()
	}
}
