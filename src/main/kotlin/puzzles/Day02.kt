package puzzles

import reader.Reader

class Day02 {
    private val rounds = Reader.input(2)

    fun p1() {
        val res = rounds
            .fold(0) { acc, round ->
                val myShape = shapeOf(round.substringAfter(' '))
                val elfShape = shapeOf(round.substringBefore(' '))
                acc + myShape.points + myShape.versus(elfShape).points
            }

        require(res == 14827)
    }

    fun p2() {
        val res = rounds
            .fold(0) { acc, round ->
                val desiredResult = resultOf(round.substringAfter(' '))
                val elfShape = shapeOf(round.substringBefore(' '))
                val myShape = shapeToUse(desiredResult, elfShape)
                acc + myShape.points + desiredResult.points
            }

        require(res == 13889)
    }

    private fun shapeOf(c: String): Shape {
        return when (c) {
            "A", "X" -> Rock
            "B", "Y" -> Paper
            else -> Scissors
        }
    }

    private fun resultOf(c: String): Result {
        return when (c) {
            "X" -> Result.LOSS
            "Y" -> Result.DRAW
            else -> Result.WIN
        }
    }

    private fun shapeToUse(result: Result, opponent: Shape): Shape {
        when (result) {
            Result.WIN -> {
                return when (opponent) {
                    is Rock -> Paper
                    is Paper -> Scissors
                    is Scissors -> Rock
                }
            }
            Result.DRAW -> {
                return opponent
            }
            Result.LOSS -> {
                return when (opponent) {
                    is Rock -> Scissors
                    is Paper -> Rock
                    is Scissors -> Paper
                }
            }
        }
    }
}

enum class Result(val points: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0)
}

object Rock : Shape() {
    override val points: Int
        get() = 1

    override fun versus(opponent: Shape): Result {
        return when (opponent) {
            is Rock -> Result.DRAW
            is Paper -> Result.LOSS
            is Scissors -> Result.WIN
        }
    }
}

object Paper : Shape() {
    override val points: Int
        get() = 2

    override fun versus(opponent: Shape): Result {
        return when (opponent) {
            is Rock -> Result.WIN
            is Paper -> Result.DRAW
            is Scissors -> Result.LOSS
        }
    }
}

object Scissors : Shape() {
    override val points: Int
        get() = 3

    override fun versus(opponent: Shape): Result {
        return when (opponent) {
            is Rock -> Result.LOSS
            is Paper -> Result.WIN
            is Scissors -> Result.DRAW
        }
    }
}

sealed class Shape {
    abstract val points: Int
    abstract fun versus(opponent: Shape): Result
}