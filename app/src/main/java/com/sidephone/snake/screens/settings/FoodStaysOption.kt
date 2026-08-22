package com.sidephone.snake.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.theme.Dimens
import com.sidephone.snake.util.gamepadClickableButton


@Composable
fun FoodStaysOption(settings: Settings) {
	var foodStaysUntilEaten by remember { mutableStateOf(settings.foodStaysUntilEaten()) }

	fun toggle(checked: Boolean) {
		foodStaysUntilEaten = checked
		settings.setFoodStaysUntilEaten(checked)
	}

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = Dimens.MainMenuButtonPaddingBottom)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.toggleable(
					value = foodStaysUntilEaten,
					onValueChange = { checked -> toggle(checked) }
				)
				.gamepadClickableButton(onClick = { toggle(!foodStaysUntilEaten) }),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = stringResource(R.string.settings_food_stays_title),
				style = typography.bodyLarge,
				color = colorScheme.onBackground,
				modifier = Modifier.weight(1f)
			)
			Switch(
				checked = foodStaysUntilEaten,
				onCheckedChange = null // Row now owns the toggle; avoids double-handling the same tap
			)
		}
		Text(
			text = stringResource(
				if (foodStaysUntilEaten) R.string.settings_food_stays_on_summary
				else R.string.settings_food_stays_off_summary
			),
			style = typography.bodySmall,
			color = colorScheme.onSurfaceVariant
		)
	}
}
