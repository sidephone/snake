package com.sidephone.snake.screens

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.sidephone.snake.R
import com.sidephone.snake.ui.theme.Dimens

@Composable
fun RecordHighScoreScreen(newScore: Int, onNameEntered: (String) -> Unit) {
	val defaultName = stringResource(R.string.record_high_score_default_name)
	var playerName by remember { mutableStateOf("") }

	Dialog(onDismissRequest = { onNameEntered(defaultName) }) {
		Card(
			modifier = Modifier.fillMaxWidth().padding(Dimens.PopupPadding),
			shape = MaterialTheme.shapes.medium
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.padding(Dimens.PopupPadding)
			) {
				Text(
					text = stringResource(R.string.record_high_score_title),
					style = MaterialTheme.typography.headlineSmall,
					modifier = Modifier.padding(vertical = Dimens.PopupPadding)
				)

				Text(
					text = stringResource(R.string.game_score, newScore),
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier.padding(bottom = Dimens.PopupPadding)
				)

				OutlinedTextField(
					value = playerName,
					onValueChange = { playerName = it },
					label = { Text(stringResource(R.string.record_high_score_enter_your_name)) },
					placeholder = { Text(defaultName) },
					singleLine = true,
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = Dimens.PopupPadding)
						.onKeyEvent(
						onKeyEvent = { keyEvent ->
							val keyCode = keyEvent.nativeKeyEvent.keyCode
							val isConfirmKey = keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BUTTON_START || keyCode == KeyEvent.KEYCODE_BUTTON_A || keyCode == KeyEvent.KEYCODE_BUTTON_B

							if (!isConfirmKey) {
								return@onKeyEvent false
							}

							if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
								var trimmedName = playerName.trim()
								if (trimmedName.isEmpty()) {
									trimmedName = defaultName
								}
								onNameEntered(trimmedName)
								true
							} else {
								false
							}
						}
					)
				)
			}
		}
	}
}
