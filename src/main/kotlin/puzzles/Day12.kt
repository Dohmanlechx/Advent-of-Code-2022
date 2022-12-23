package puzzles

import reader.Reader

typealias Position = Pair<Int, Int>
typealias Hill = Array<Array<Char>>

private fun main() {
    p1()
    p2()
}

private val input = Reader.example(12)

private val ALREADY_TESTED = '.'

val alreadyVisited = mutableListOf<Position>()

private fun p1() {
    val rowSize = input[0].count()
    val columnSize = input.count()

    val hill = Array(columnSize) { Array(rowSize) { 'x' } }

    for (x in 0 until columnSize) {
        for (y in 0 until rowSize) {
            hill[x][y] = input[x][y]
        }
    }





    var climber: Position = hill.startPosition()



    do {
        hill.forEach {
            it.forEach { print(it) }
            println()
        }
        println()

        val next = next(hill, climber)
        climber = Position(climber.first + next.first, climber.second + next.second)
    } while (hill[climber.first][climber.second] != 'z')

    println("Done")

}

private fun p2() {

}

private fun next(hill: Hill, climber: Position): Position {
    val res = listOf(
        Position(climber.first - 1, climber.second), // UP
        Position(climber.first + 1, climber.second), // DOWN
        Position(climber.first, climber.second - 1), // LEFT
        Position(climber.first, climber.second + 1), // RIGHT
    ).find {
        val clim = hill[climber.first][climber.second]
        val elevation = hill[maxOf(0, minOf(it.first, 4))][maxOf(0, minOf(it.second, 7))]
        it !in alreadyVisited && clim isOneLowerThan elevation || (climber == hill.startPosition() && elevation == 'a')
    } ?: hill.startPosition()

    if (res == hill.startPosition()) {
        alreadyVisited.add(climber)
    }

    return res
}

private fun Hill.startPosition(): Position {
    val climberY = indexOfFirst { it.contains('S') }
    val climberX = this[climberY].indexOfFirst { it == 'S' }
    return Position(climberY, climberX)
}

private infix fun Char.isOneLowerThan(other: Char): Boolean =
  this == other ||  (this + 1) == other