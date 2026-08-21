package com.sidephone.snake.engine

class HighScores {
	companion object {
		const val MAX = 10
	}
	private val scores = mutableListOf<Pair<String, Int>>()

	fun addScore(name: String, score: Int) {
		scores.add(name to score)
		scores.sortByDescending { it.second }
		if (scores.size > MAX) {
			scores.subList(MAX, scores.size).clear()
		}
	}


	fun clear() {
		scores.clear()
	}


	fun getAll(): List<Pair<String, Int>> {
		return scores.toList()
	}


	fun isEmpty(): Boolean {
		return scores.isEmpty()
	}


	fun isHighScore(score: Int): Boolean {
		return scores.isEmpty() || score > scores.last().second
	}
}
