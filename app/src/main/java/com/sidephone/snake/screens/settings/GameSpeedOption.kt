package com.sidephone.snake.screens.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.gamepadClickableButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSpeedDropdown(
	initialSpeed: Int,
	onSpeedSelected: (Int) -> Unit
) {
	var expanded by remember { mutableStateOf(false) }
	var selectedSpeed by remember { mutableIntStateOf(initialSpeed) }

	val speedOptions = (Settings.GAME_SPEED_MIN..Settings.GAME_SPEED_MAX step 10).toList()

	ExposedDropdownMenuBox(
		modifier = Modifier.fillMaxWidth().padding(top = Dimens.MainMenuButtonPaddingBottom),
		expanded = expanded,
		onExpandedChange = { expanded = it }
	) {
		OutlinedTextField(
			modifier = Modifier
				.fillMaxWidth()
				.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
				.gamepadClickableButton { expanded = true },
			readOnly = true,
			value = selectedSpeed.toString(),
			onValueChange = {},
			label = { Text(stringResource(R.string.settings_game_speed_title)) },
			trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
		)

		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
			modifier = Modifier.exposedDropdownSize()
		) {
			speedOptions.forEach { speed ->
DropdownMenuItem(
					modifier = Modifier.gamepadClickableButton {
						selectedSpeed = speed
						expanded = false
						onSpeedSelected(speed)
					},
					text = { Text(speed.toString()) },
					onClick = {
						selectedSpeed = speed
						expanded = false
						onSpeedSelected(speed)
					}
				)
			}
		}
	}
}
