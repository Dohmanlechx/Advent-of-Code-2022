package puzzles

import reader.Reader

class Day06 {
    private val data = Reader.input(6).joinToString()

    fun p1() {
        val res = data.marker(len = 4)
        require(res == 1794)
    }

    fun p2() {
        val res = data.marker(len = 14)
        require(res == 2851)
    }

    private fun String.marker(len: Int): Int {
        for (i in len - 1 until count()) {
            if (List(len) { this[i - it] }.distinct().count() == len) {
                return i + 1
            }
        }

        return -1
    }
}