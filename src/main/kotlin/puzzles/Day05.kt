package puzzles

import reader.Reader

typealias Stack = MutableList<Char>

class Day05 {
    private val instructions =
        Reader.input(5, split = "\n\n")[1]
            .filter { it.isDigit() || it.isWhitespace() }
            .split("\n")
            .map(String::trim)

    private fun crates(): Map<Int, Stack> =
        mapOf(
            1 to mutableListOf('B', 'S', 'V', 'Z', 'G', 'P', 'W'),
            2 to mutableListOf('J', 'V', 'B', 'C', 'Z', 'F'),
            3 to mutableListOf('V', 'L', 'M', 'H', 'N', 'Z', 'D', 'C'),
            4 to mutableListOf('L', 'D', 'M', 'Z', 'P', 'F', 'J', 'B'),
            5 to mutableListOf('V', 'F', 'C', 'G', 'J', 'B', 'Q', 'H'),
            6 to mutableListOf('G', 'F', 'Q', 'T', 'S', 'L', 'B'),
            7 to mutableListOf('L', 'G', 'C', 'Z', 'V'),
            8 to mutableListOf('N', 'L', 'G'),
            9 to mutableListOf('J', 'F', 'H', 'C')
        )

    fun p1() {
        val crates = crates()

        for (instruction in instructions) {
            instruction.execute { count, from, to ->
                val a = crates.getValue(from)
                val b = crates.getValue(to)
                repeat(count) { a oneCrateTo b }
            }
        }

        val res = crates.values.tops()
        require(res == "VJSFHWGFT")
    }

    fun p2() {
        val crates = crates()

        for (instruction in instructions) {
            instruction.execute { count, from, to ->
                val a = crates.getValue(from)
                val b = crates.getValue(to)
                a.cratesTo(b, count)
            }

        }

        val res = crates.values.tops()
        require(res == "LCTQFBVZV")
    }

    private fun String.execute(action: (Int, Int, Int) -> Unit) {
        val digits = split("  ").map(String::toInt)
        action(digits[0], digits[1], digits[2])
    }

    private infix fun Stack.oneCrateTo(other: Stack) {
        val item = removeLast()
        other.add(item)
    }

    private fun Stack.cratesTo(other: Stack, count: Int) {
        val items = mutableListOf<Char>()
        repeat(count) { items.add(removeLast()) }
        other.addAll(items.reversed())
    }

    private fun Collection<Stack>.tops(): String =
        map(Stack::last).joinToString("")

}