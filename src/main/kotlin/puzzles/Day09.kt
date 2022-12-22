package puzzles

import reader.Reader
import kotlin.math.abs

private fun main() {
    p1()
    p2()
}

private val input = Reader.input(9)

private fun p1() {
    val res = solve(knotCount = 2)
    require(res == 6098)
}

private fun p2() {
    // I give up.
}

private fun solve(knotCount: Int): Int {
    val rope = Rope(knotCount)

    for (instruction in input) {
        val (direction, steps) = instruction.split(" ")
        rope.move(direction, steps.toInt())

    }

    return rope.visitedPositionsOfTail()
}

class Rope(size: Int) {
    private val knots = List(size) { Knot(0, 0) }

    fun move(direction: String, steps: Int) {
        repeat(steps) {
            for (i in knots.indices) {
                val knot = knots[i]
                var previousKnot: Knot? = null

                if (i > 0) {
                    previousKnot = knots[i - 1]
                }

                if (!knot.isTouching(previousKnot)) {
                    when (direction) {
                        "U" -> knot.up(previousKnot)
                        "D" -> knot.down(previousKnot)
                        "L" -> knot.left(previousKnot)
                        "R" -> knot.right(previousKnot)
                    }
                }
            }
        }
    }

    fun visitedPositionsOfTail(): Int =
        knots.last().countOfUniquePositions()
}

class Knot(private var y: Int, private var x: Int) {
    private val visitedPositions: MutableList<Pair<Int, Int>> =
        mutableListOf(Pair(0, 0))

    fun countOfUniquePositions(): Int =
        visitedPositions.toSet().count()

    fun isTouching(other: Knot?): Boolean =
        other != null && abs(this.x - other.x) <= 1 && abs(this.y - other.y) <= 1

    fun up(previousKnot: Knot? = null) {
        x--

        if (previousKnot != null && y != previousKnot.y) {
            y = previousKnot.y
        }

        visitedPositions.add(Pair(x, y))
    }

    fun down(previousKnot: Knot? = null) {
        x++

        if (previousKnot != null && y != previousKnot.y) {
            y = previousKnot.y
        }

        visitedPositions.add(Pair(x, y))
    }

    fun left(previousKnot: Knot? = null) {
        y--

        if (previousKnot != null && x != previousKnot.x) {
            x = previousKnot.x
        }

        visitedPositions.add(Pair(x, y))
    }

    fun right(previousKnot: Knot? = null) {
        y++

        if (previousKnot != null && x != previousKnot.x) {
            x = previousKnot.x
        }

        visitedPositions.add(Pair(x, y))
    }
}