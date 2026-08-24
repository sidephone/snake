package com.sidephone.snake.engine

import android.util.Log
import android.view.KeyEvent
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.sidephone.snake.engine.entities.Food
import com.sidephone.snake.engine.entities.Ground
import com.sidephone.snake.engine.entities.Snake
import com.sidephone.snake.engine.graphics.DrawCommand
import com.sidephone.snake.engine.graphics.GameFrame
import com.sidephone.snake.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.math.roundToInt


/**
 * The main game engine class. It contains the game loop, input handling, and game state management.
 * It is designed to be simple and easy to understand, so you can modify it to create your own game.
 */
class Gameplay(private val settings: Settings?) {
	companion object {
		private val LOG_TAG = Gameplay::class.java.simpleName
	}

	// game loop
	private var foodStaysUntilEaten = true
	private var executor = Executors.newSingleThreadScheduledExecutor()
	private var engineLooper: Future<*>? = null
	@Volatile private var isPaused = false
	@Volatile private var speedFactor = 100

	// input
	@Volatile private var pressedKeys = setOf<Int>()

	// output
	private var onStartButtonPressed: (wasGameOver: Boolean, score: Int) -> Unit = { _, _ -> }
	private var onStarted = {}

	private val _isGameOver = MutableStateFlow(false)
	val isGameOver: StateFlow<Boolean> = _isGameOver

	private val _score = MutableStateFlow(0)
	val score: StateFlow<Int> = _score

	// graphics
	@Volatile private var viewportWidth = 1f
	@Volatile private var viewportHeight = 1f
	@Volatile var currentFrame: GameFrame = GameFrame()
	@Volatile private var firstIteration = true
	private var iteration = 0

	// game objects
	private val snake = Snake()
	private val food = Food()


	init {
	    reset()
	}


	/**
	 * Set the initial state of the game. Call this whenever you need to restart the game.
	 */
	@MainThread
	fun reset() {
		if (!isGameThreadAlive()) {
			if (!executor.isShutdown && !executor.isTerminated) {
				executor.shutdownNow()
			}
		}

		currentFrame = GameFrame(Ground.BACKGROUND, emptyList())
		pressedKeys = setOf()

		iteration = 0
		_isGameOver.value = false
		_score.value = 0

		food.destroy()
		foodStaysUntilEaten = settings?.foodStaysUntilEaten() ?: foodStaysUntilEaten
		snake.spawn(viewportWidth, viewportHeight)
	}


	/**
	 * Handle the pressed keys for your game logic.
	 * For each key you can call appropriate handler. E.g. if KeyEvent.KEYCODE_DPAD_UP, call
	 * "moveUp()" function, or if KeyEvent.KEYCODE_BUTTON_A, call "jump()" function. You can also
	 * choose to ignore some keys if you don't need them for your game.
	 * When a key is released, you will receive a new list of pressed keys without that key.
	 *
	 * @param keys The set of currently pressed keys represented by their KeyEvent key codes.
	 */
	@MainThread
	fun onPressedKeys(keys: Set<Int>) {
		pressedKeys = keys.toSet() // make a copy for thread safety
		preprocessInput()
	}


	@MainThread
	fun setSpeed(speed: Int) {
		val gameSpeed = speed.coerceIn(Settings.GAME_SPEED_MIN, Settings.GAME_SPEED_MAX)
		speedFactor = (100f / gameSpeed).roundToInt().coerceAtLeast(1)
		Log.d(LOG_TAG, "Game speed changed to $gameSpeed, factor=$speedFactor")
	}


	/**
	 * Adjust the dimension of the game scene. All rendering will be performed using these.
	 */
	@AnyThread
	fun setViewportSize(width: Int, height: Int) {
		if (width <= 0 || height <= 0) {
			Log.w(LOG_TAG, "Ignoring invalid viewport size: width=$width, height=$height. Must be positive.")
			return
		}

		viewportWidth = width.toFloat()
		viewportHeight = height.toFloat()
	}


	/**
	 * Start or resume the game loop, or if already running, do nothing.
	 */
	@MainThread
	fun start() {
		if (isGameThreadAlive()) {
			return
		}

		if (executor.isShutdown || executor.isTerminated) executor = Executors.newSingleThreadScheduledExecutor()
		isPaused = false
		firstIteration = true

		engineLooper = executor.scheduleWithFixedDelay(
			{ advance() },
			0,
			1000L / Settings.Gameplay.TARGET_IPS,
			java.util.concurrent.TimeUnit.MILLISECONDS
		)

		onStarted()

		Log.d(LOG_TAG, "Gameplay loop started at ${Settings.Gameplay.TARGET_IPS} iterations per second")
	}


	/**
	 * Pause the game loop, or if already paused, do nothing.
	 */
	@MainThread
	fun pause() {
		if (isPaused) {
			return
		}

		engineLooper?.cancel(true)
		isPaused = true

		Log.d(LOG_TAG, "Gameplay loop paused")
	}


	/**
	 * Stop the game loop and release resources. After calling this, you can not resume the game
	 * anymore, you can only use "reset()" to start a new game.
	 */
	@MainThread
	fun stop() {
		isPaused = false
		executor.shutdownNow()
		Log.d(LOG_TAG, "Gameplay loop stopped")
	}


	/**
	 * A utility function that returns true if the game loop is currently running.
	 */
	@MainThread
	fun isRunning(): Boolean {
		return !isPaused && isGameThreadAlive()
	}


	/**
	 * A utility function that returns true if the game loop is currently paused.
	 */
	@AnyThread
	fun isPaused(): Boolean {
		return isPaused
	}


	@MainThread
	fun onStartButton() {
		val wasGameOver = _isGameOver.value
		val currentScore = _score.value

		if (_isGameOver.value) {
			stop()
			reset()
		} else {
			pause()
		}

		onStartButtonPressed(wasGameOver, currentScore)
	}


	/**
	 * Set an optional callback to be invoked when the game is paused. This can be used to navigate
	 * back to the main menu or perform other actions.
	 */
	@MainThread
	fun setOnStartButtonPressedCallback(callback: (wasGameOver: Boolean, score: Int) -> Unit): Gameplay {
		onStartButtonPressed = callback
		return this
	}


	/**
	 * Set an optional callback to be invoked immediately before the game starts.
	 */
	@MainThread
	fun setOnStartedCallback(callback: () -> Unit): Gameplay {
		onStarted = callback
		return this
	}


	/**
	 * Returns true when the game thread executor is still working.
	 */
	@AnyThread
	private fun isGameThreadAlive(): Boolean {
		return !executor.isShutdown && !executor.isTerminated && (engineLooper?.isDone == false)
	}


	/**
	 * The main game loop function. This is equivalent to a single step or "frame" in the game. It
	 * is called repeatedly at a fixed interval to read the input, update state and perform other game
	 * logic. Finally, the "render()" method draws the current state to the screen.
	 */
	@WorkerThread
	private fun advance() {
		try {
			// process input on every iteration to ensure responsiveness
			processInput()

			// the standard movement speed is too fast to be playable, so we skip rendering some frames,
			// to reduce the perceived game speed
			iteration++
			if (iteration % speedFactor == 0) {
				render()
			}
		} catch (e: Exception) {
			Log.e(LOG_TAG, "Failed advancing ahead gameplay. ${e.message}", e)
		}
	}


	/**
	 * Perform any non-game related actions, immediately after receiving the pressed keys. For example,
	 * pause the game, when "KeyEvent.KEYCODE_BUTTON_START" is pressed.
	 */
	@MainThread
	private fun preprocessInput() {
		if (KeyEvent.KEYCODE_BUTTON_START in pressedKeys) {
			onStartButton()
		}
	}


	/**
	 * Set any game state variables based on the currently pressed keys. This is the first step in
	 * the game loop. All following steps will use these variables to calculate actions or draw objects.
	 * on the screen.
	 */
	@WorkerThread
	private fun processInput() {
		val keys = pressedKeys // make a copy for thread safety

		val up = KeyEvent.KEYCODE_DPAD_UP in keys
		val right = KeyEvent.KEYCODE_DPAD_RIGHT in keys
		val down = KeyEvent.KEYCODE_DPAD_DOWN in keys
		val left = KeyEvent.KEYCODE_DPAD_LEFT in keys

		if (up && !down) {
			snake.turn(Snake.Direction.UP)
		} else if (down && !up) {
			snake.turn(Snake.Direction.DOWN)
		} else if (left && !right) {
			snake.turn(Snake.Direction.LEFT)
		} else if (right && !left) {
			snake.turn(Snake.Direction.RIGHT)
		}
	}


	/**
	 * This is the main method that draws to the screen.
	 */
	@WorkerThread
	private fun render() {
		if (isGameOver.value) {
			return
		}

		val now = System.currentTimeMillis()
		var isSceneChanged: Boolean

		snake.move()
		snake.toggleTongueRandomly(now, pressedKeys.isNotEmpty())

		if (snake.isAlive(viewportWidth, viewportHeight)) {
			isSceneChanged = true
		} else {
			isSceneChanged = !isGameOver.value
			_isGameOver.value = true
		}

		if (snake.canEat(food) && food.exists(now)) {
			snake.eat(food, now)
			food.destroy()
			_score.value = snake.segments() - Snake.INITIAL_LENGTH.roundToInt()
			isSceneChanged = true
		}

		if (!food.exists(now)) {
			food.spawn(viewportWidth, viewportHeight, now, foodStaysUntilEaten)
			isSceneChanged = true
		}

		if (firstIteration) {
			firstIteration = false
			isSceneChanged = true
		}

		if (!isSceneChanged) {
			return
		}

		val drawCommands = mutableListOf<DrawCommand>()
		drawCommands.addAll(snake.draw(now))
		drawCommands.addAll(food.draw())
		currentFrame = GameFrame(Ground.BACKGROUND, drawCommands)
	}
}
