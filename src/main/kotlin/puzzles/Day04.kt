package puzzles

import reader.Reader

private fun main() {
    p1()
    p2()
}

private val pairs = Reader.input(4).toPairList()

private fun p1() {
    val res = pairs.count { pair ->
        pair.first.all { it in pair.second } || pair.second.all { it in pair.first }
    }

    require(res == 651)
}

private fun p2() {
    val res = pairs.count { pair ->
        pair.first.any { it in pair.second } || pair.second.any { it in pair.first }
    }

    require(res == 956)
}

private fun List<String>.toPairList(): List<Pair<IntRange, IntRange>> =
    map {
        val a1 = it.substringBefore(',').substringBefore('-').toInt()
        val a2 = it.substringBefore(',').substringAfter('-').toInt()
        val b1 = it.substringAfter(',').substringBefore('-').toInt()
        val b2 = it.substringAfter(',').substringAfter('-').toInt()
        Pair(a1..a2, b1..b2)
    }