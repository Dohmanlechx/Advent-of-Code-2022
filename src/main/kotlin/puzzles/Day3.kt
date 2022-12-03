package puzzles

import reader.Reader

class Day3 {
    private val rucksacks = Reader.input(3)

    private val priorities =
        ('a'..'z').associateWith { it.code - 96 } + ('A'..'Z').associateWith { it.code - 38 }

    fun p1() {
        val res =
            rucksacks.fold(0) { acc, rucksack ->
                val compartments = rucksack.chunked(rucksack.length / 2)
                val commonItem = compartments[0].find { it in compartments[1] }
                acc + (priorities[commonItem] ?: 0)
            }

        require(res == 8298)
    }

    fun p2() {
        var res = 0

        for (group in rucksacks.chunked(3)) {
            for (item in group[0]) {
                if (item in group[1] && item in group[2]) {
                    res += priorities[item] ?: 0
                    break
                }
            }
        }

        require(res == 2708)
    }
}