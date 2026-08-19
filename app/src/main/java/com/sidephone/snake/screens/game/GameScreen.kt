package com.sidephone.snake.screens.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sidephone.snake.engine.Gameplay
import androidx.compose.foundation.layout.padding


@Composable
fun GameScreen(gameplay: Gameplay) {
	val menuBackground = MaterialTheme.colorScheme.background.toArgb()
//	val score by gameplay.isGameOver.collectAsState() // needs a StateFlow<Int> on Gameplay

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context -> GameSurfaceView(context, gameplay, menuBackground) },
	)

//	Text(	text = "Score: 50", color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(16.dp))
}
