package com.sidephone.snake

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sidephone.snake.engine.Gamepad
import com.sidephone.snake.engine.Gameplay
import com.sidephone.snake.engine.HighScores
import com.sidephone.snake.screens.HighScoresScreen
import com.sidephone.snake.screens.MainMenuScreen
import com.sidephone.snake.screens.RecordHighScoreScreen
import com.sidephone.snake.screens.ScreenType
import com.sidephone.snake.screens.game.GameScreen
import com.sidephone.snake.settings.SettingsStore
import com.sidephone.snake.ui.theme.SidesnakeTheme

class MainActivity : ComponentActivity() {
	private var gamepad = Gamepad()
	private var gameplay = Gameplay()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()
		setContent {
			SidesnakeTheme {
				val settings = SettingsStore(this)

				var currentScreen by remember { mutableStateOf(ScreenType.Menu) }
				var isGamePaused by remember { mutableStateOf(false) }
				var recordHighScore by remember { mutableStateOf<Int?>(null) }
				val highScores = HighScores()

				settings.loadHighScores(highScores)

				// Back button/gesture returns to the menu from any sub-screen
				BackHandler(enabled = currentScreen != ScreenType.Menu) {
					if (currentScreen == ScreenType.Game) {
						gameplay.onStartButton()
					} else {
						if (currentScreen == ScreenType.RecordHighScore) {
							recordHighScore = null
						}
						currentScreen = ScreenType.Menu
					}
				}

				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					Box(modifier = Modifier.padding(innerPadding)) {
						GameScreen(gameplay, currentScreen) // Keep this in memory due to an Android bug. See below.

						when (currentScreen) {
							ScreenType.Menu -> MainMenuScreen(
								isGamePaused = isGamePaused,
								onExit = { finish() },
								onHighScores = { currentScreen = ScreenType.HighScores },
								onEndGame = {
									gameplay.stop()
									isGamePaused = gameplay.isPaused()
								},
								onNewGame = {
									currentScreen = ScreenType.Game

									gamepad.reset()

									if (!gameplay.isPaused()) gameplay.reset()

									gameplay
										.setOnStartButtonPressedCallback { isGameOver, score ->
											isGamePaused = gameplay.isPaused()
											if (isGameOver && highScores.isHighScore(score)) {
												recordHighScore = score
												currentScreen = ScreenType.RecordHighScore
											} else {
												currentScreen = ScreenType.Menu
											}
										}
										.start()
								},
							)
							ScreenType.HighScores -> { HighScoresScreen(highScores) }
							ScreenType.RecordHighScore -> RecordHighScoreScreen(
								newScore = recordHighScore ?: 0,
								onNameEntered = { playerName ->
									highScores.addScore(playerName, recordHighScore ?: 0)
									settings.saveHighScores(highScores)
									recordHighScore = null
									currentScreen = ScreenType.Menu
								}
							)
							ScreenType.Game -> {
								// Due to an Android bug, we initialize the screen at the beginning and keep the
								// object alive all the time. Otherwise, we can't make it render after returning from
								// paused state, because its surfaceCreated() method is not called again.
								// See: https://slack-chats.kotlinlang.org/t/12312231/funky-issue-i-ve-got-i-m-using-androidview-with-a-surfacevie
								// See: https://issuetracker.google.com/issues/285718058
							}
						}
					}
				}
			}
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		gameplay.stop()
	}


	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (gameplay.isRunning() && gamepad.onKeyDown(keyCode, event)) {
			gameplay.onPressedKeys(gamepad.pressedKeys)
			return true
		}

		return super.onKeyDown(keyCode, event)
	}


	override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
		if (gameplay.isRunning() && gamepad.onKeyUp(keyCode)) {
			gameplay.onPressedKeys(gamepad.pressedKeys)
			return true
		}

		return super.onKeyUp(keyCode, event)
	}
}
