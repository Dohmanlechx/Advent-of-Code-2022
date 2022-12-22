package puzzles

import reader.Reader

private fun main() {
    p1()
    p2()
}

private val input = Reader.input(11, split = "\r\n")

private fun p1() {
    val monkeys =
        input
            .filterNot(String::isEmpty)
            .chunked(6)
            .map(Monkey::fromData)

    fun updateMonkeyItem(mi: Int, ii: Int, item: Int) {
        monkeys[mi].items[ii] = item
    }

    repeat(20) {
        monkeys.forEachIndexed { mi, monkey ->
            monkey.items.forEachIndexed { ii, item ->
                updateMonkeyItem(mi, ii, monkey.operation(item))
                updateMonkeyItem(mi, ii, monkey.items[ii] / 3)

                val monkeyIndex = when {
                    monkey.items[ii] % monkey.divisibleBy == 0 -> monkey.throwToMonkeyIfTrue
                    else -> monkey.throwToMonkeyIfFalse
                }

                monkeys[monkeyIndex].items.add(monkey.items[ii])

                monkey.inspected++
            }
            monkey.items.clear()
        }
    }

    val res =
        monkeys
            .sortedBy(Monkey::inspected)
            .takeLast(2)
            .let { it[0].inspected * it[1].inspected }

    require(res == 113220)
}

private fun p2() {
    // I give up.
}

data class Monkey(
    val id: String,
    val items: MutableList<Int>,
    val operation: (Int) -> Int,
    val divisibleBy: Int,
    val throwToMonkeyIfTrue: Int,
    val throwToMonkeyIfFalse: Int,
    var inspected: Int = 0,
) {
    companion object {
        fun fromData(input: List<String>): Monkey {
            fun buildOperation(line: String): (Int) -> Int {
                val op = when {
                    line.contains("+") -> "+"
                    line.contains("-") -> "-"
                    line.contains("*") -> "*"
                    else -> throw Exception("Unknown operation")
                }

                val (x, y) = line.split(" $op ")

                if (x.contains("old") && y.contains("old")) {
                    return when (op) {
                        "+" -> { a -> a + a }
                        "-" -> { a -> a - a }
                        else -> { a -> a * a }
                    }
                } else if (x.contains("old") && !y.contains("old")) {
                    return when (op) {
                        "+" -> { a -> a + y.toInt() }
                        "-" -> { a -> a - y.toInt() }
                        else -> { a -> a * y.toInt() }
                    }
                } else {
                    return when (op) {
                        "+" -> { a -> x.substringAfter("= ").toInt() + a }
                        "-" -> { a -> x.substringAfter("= ").toInt() - a }
                        else -> { a -> x.substringAfter("= ").toInt() * a }
                    }
                }
            }

            val id = input[0].substringAfter("Monkey ").substringBefore(":")
            val startingItems = input[1].substringAfter(": ").split(", ").map(String::toInt).toMutableList()
            val operation = buildOperation(input[2])
            val divisibleBy = input[3].substringAfter("by ").toInt()
            val throwToMonkeyIfTrue = input[4].substringAfter("monkey ").toInt()
            val throwToMonkeyIfFalse = input[5].substringAfter("monkey ").toInt()

            return Monkey(
                id,
                startingItems,
                operation,
                divisibleBy,
                throwToMonkeyIfTrue,
                throwToMonkeyIfFalse
            )
        }
    }

    override fun toString(): String {
        return "Monkey #$id:\n" +
                "Items: $items\n" +
                "Divisible by: $divisibleBy\n" +
                "Throw to if true: $throwToMonkeyIfTrue\n" +
                "Throw to if false: $throwToMonkeyIfFalse\n" +
                "Inspected items $inspected times\n" +
                "........................"
    }
}