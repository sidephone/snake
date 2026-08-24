package com.sidephone.snake.screens.settings

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sidephone.snake.R
import com.sidephone.snake.settings.Settings
import com.sidephone.snake.ui.modifiers.gamepadClickableButton
import com.sidephone.snake.ui.theme.Dimens


@Composable
fun FoodStaysOption(settings: Settings) {
	var foodStaysUntilEaten by remember { mutableStateOf(settings.foodStaysUntilEaten()) }
	val interactionSource = remember { MutableInteractionSource() }
	val isFocused by interactionSource.collectIsFocusedAsState()

	fun toggle(checked: Boolean) {
		foodStaysUntilEaten = checked
		settings.setFoodStaysUntilEaten(checked)
	}

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(Dimens.SettingsPreferenceWrapper)
	) {
		Row(
			modifier = Modifier
				.clip(RoundedCornerShape(Dimens.SettingsPreferencePadding))
				.background(
					color = if (isFocused) colorScheme.secondary
					        else Color.Transparent
				)
				.toggleable(
					value = foodStaysUntilEaten,
					interactionSource = interactionSource,
					indication = LocalIndication.current,
					onValueChange = { checked -> toggle(checked) }
				)
				.gamepadClickableButton(onClick = { toggle(!foodStaysUntilEaten) }),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			// labels
			Column(
				modifier = Modifier
					.weight(1f)
					.padding(start = Dimens.SettingsPreferencePadding, top = Dimens.SettingsPreferencePadding, bottom = Dimens.SettingsPreferencePadding)
			) {
				Text(
					text = stringResource(R.string.settings_food_stays_title),
					style = typography.bodyLarge,
					color = if (isFocused) colorScheme.onSecondary
					        else colorScheme.onBackground,
				)
				Text(
					text = stringResource(
						if (foodStaysUntilEaten) R.string.settings_food_stays_on_summary
						else R.string.settings_food_stays_off_summary
					),
					style = typography.bodySmall,
					color = if (isFocused) colorScheme.onSecondary
					        else colorScheme.onSurfaceVariant
				)
			}

			Switch(
				modifier = Modifier.padding(horizontal = Dimens.SettingsPreferenceSwitchPadding),
				checked = foodStaysUntilEaten,
				onCheckedChange = null
			)
		}
	}
}
