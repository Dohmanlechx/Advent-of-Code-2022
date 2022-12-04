package puzzles

import reader.Reader

class Day01 {
    fun p1() {
        val res = scoreboard().first()
        require(res == 75501)
    }

    fun p2() {
        val res = scoreboard().take(3).sum()
        require(res == 215594)
    }

    private fun scoreboard(): List<Int> =
        Reader.input(1)
            .joinToString(" ")
            .split("  ")
            .map { it.split(" ").map(String::toInt).sum() }
            .sortedDescending()
}