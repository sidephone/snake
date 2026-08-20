package com.sidephone.snake.engine

class HighScores {
	companion object {
		private const val MAX = 10
	}
	var scores: MutableList<Pair<String, Int>> = mutableListOf()


	fun addScore(name: String, score: Int) {
		scores.add(Pair(name, score))
		scores.sortByDescending { it.second }
		if (scores.size > MAX) {
			scores = scores.take(MAX).toMutableList()
		}
	}


	fun getAll(): List<Pair<String, Int>> {
		return scores
	}


	fun isEmpty(): Boolean {
		return scores.isEmpty()
	}


	fun isHighScore(score: Int): Boolean {
		return scores.isEmpty() || score > scores.first().second
	}
}
