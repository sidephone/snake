package com.sidephone.snake.engine

import android.view.KeyEvent


/**
 * Represents the gamepad controller. It takes input from any Activity.onKeyDown, Activity.onKeyUp,
 * then stores the pressed keys in a set, to be used by other game components. The keys are represented
 * by their KeyEvent key codes, and remain in the set for as long as the user holds the button down.
 * When the button is released, the key is removed from the set.
 * This class already supports all gamepad buttons, so you should not need to modify it.
 */
class Gamepad {
	val pressedKeys = mutableSetOf<Int>()


	fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (hasKey(keyCode) && event?.repeatCount == 0) {
			pressedKeys.add(normalizeKeyCode(keyCode))
			return true
		}

		return false
	}


	fun onKeyUp(keyCode: Int): Boolean {
		if (hasKey(keyCode)) {
			pressedKeys.remove(normalizeKeyCode(keyCode))
			return true
		}

		return false
	}


	/**
	 * Clears the list of pressed keys. Useful if restarting the game loop, for example, after pause.
	 */
	fun reset() {
		pressedKeys.clear()
	}


	/**
	 * Returns true when we want to handle this key, or false when we want to ignore it.
	 */
	private fun hasKey(keyCode: Int): Boolean {
		return when (keyCode) {
			// compact-qwerty
			KeyEvent.KEYCODE_T,
			KeyEvent.KEYCODE_D,
			KeyEvent.KEYCODE_J,
			KeyEvent.KEYCODE_B,
			KeyEvent.KEYCODE_ENTER,
			// numpad
			KeyEvent.KEYCODE_2,
			KeyEvent.KEYCODE_4,
			KeyEvent.KEYCODE_6,
			KeyEvent.KEYCODE_8,
			KeyEvent.KEYCODE_DPAD_CENTER,
			// sundial
			KeyEvent.KEYCODE_MEDIA_NEXT,
			KeyEvent.KEYCODE_MEDIA_PREVIOUS,
			KeyEvent.KEYCODE_MEDIA_PAUSE,
			// gamepad
			KeyEvent.KEYCODE_BUTTON_A,
			KeyEvent.KEYCODE_BUTTON_B,
			KeyEvent.KEYCODE_BUTTON_X,
			KeyEvent.KEYCODE_BUTTON_Y,
			KeyEvent.KEYCODE_BUTTON_SELECT,
			KeyEvent.KEYCODE_BUTTON_START,
			KeyEvent.KEYCODE_DPAD_UP,
			KeyEvent.KEYCODE_DPAD_LEFT,
			KeyEvent.KEYCODE_DPAD_RIGHT,
			KeyEvent.KEYCODE_DPAD_DOWN -> true
			else -> false
		}
	}


	/**
	 * Normalizes other tiles' key codes to the corresponding gamepad key codes
	 */
	private fun normalizeKeyCode(keyCode: Int): Int {
		return when (keyCode) {
			KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_MEDIA_PAUSE -> KeyEvent.KEYCODE_BUTTON_START
			KeyEvent.KEYCODE_T, KeyEvent.KEYCODE_2 -> KeyEvent.KEYCODE_DPAD_UP
			KeyEvent.KEYCODE_D, KeyEvent.KEYCODE_4, KeyEvent.KEYCODE_MEDIA_PREVIOUS -> KeyEvent.KEYCODE_DPAD_LEFT
			KeyEvent.KEYCODE_J, KeyEvent.KEYCODE_6, KeyEvent.KEYCODE_MEDIA_NEXT -> KeyEvent.KEYCODE_DPAD_RIGHT
			KeyEvent.KEYCODE_B, KeyEvent.KEYCODE_8 -> KeyEvent.KEYCODE_DPAD_DOWN
			else -> keyCode
		}
	}
}
