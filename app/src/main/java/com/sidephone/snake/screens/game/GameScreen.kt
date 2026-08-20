package com.sidephone.snake.screens.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.sidephone.snake.engine.Gameplay
import com.sidephone.snake.screens.ScreenType


@Composable
fun GameScreen(gameplay: Gameplay, currentScreen: ScreenType) {
	val hudTextColor = Color.White
	val hudGameOverBackground = Color.Black.copy(alpha = 0.4f)
	val menuBackground = MaterialTheme.colorScheme.background.toArgb()
	val isGameOver by gameplay.isGameOver.collectAsState()

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context -> GameSurfaceView(context, gameplay, menuBackground) },
	)

	if (currentScreen != ScreenType.Game) {
		return
	}

	if (isGameOver) {
		GameOverOverlay(hudTextColor, hudGameOverBackground)
	}

	ScoreOverlay(hudTextColor, gameplay)
}
