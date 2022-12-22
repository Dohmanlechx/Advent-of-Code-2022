package puzzles

import reader.Reader

private fun main() {
    p1()
    p2()
}

private val forest = Forest(input = Reader.input(8))

private fun p1() {
    val res = forest.countOfVisibleTreesFromOutside()
    require(res == 1789)
}

private fun p2() {
    val res = forest.highestTreeScenicScore()
    require(res == 314820)
}

data class Forest(val input: List<String>) {
    private val size = input[0].length
    private val trees = Array(size) { Array(size) { -1 } }

    init {
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, tree ->
                trees[i][j] = tree.digitToInt()
            }
        }
    }

    fun countOfVisibleTreesFromOutside(): Int {
        var countOfVisibleTrees = 0

        for (i in 0 until size) {
            for (j in 0 until size) {
                if (checkIfTreeVisibleFromAnyDirection(i, j)) {
                    countOfVisibleTrees++
                }
            }
        }

        return countOfVisibleTrees
    }

    fun highestTreeScenicScore(): Int {
        val scenicScores = mutableListOf<Int>()

        for (i in 1 until size - 1) {
            for (j in 1 until size - 1) {
                scenicScores.add(scenicScoreOfTree(i, j))
            }
        }

        return scenicScores.max()
    }

    private fun checkIfTreeVisibleFromAnyDirection(x: Int, y: Int): Boolean {
        val t = trees[x][y]

        // Up
        for (i in x - 1 downTo 0) {
            if (t <= trees[i][y]) {
                // Down
                for (i in x + 1 until size) {
                    if (t <= trees[i][y]) {
                        // Left
                        for (j in y - 1 downTo 0) {
                            if (t <= trees[x][j]) {
                                // Right
                                for (j in y + 1 until size) {
                                    if (t <= trees[x][j]) {
                                        return false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true
    }

    private fun scenicScoreOfTree(x: Int, y: Int): Int {
        val t = trees[x][y]
        var up = 0
        var down = 0
        var left = 0
        var right = 0

        for (i in x - 1 downTo 0) {
            up++
            if (t <= trees[i][y]) break
        }
        for (i in x + 1 until size) {
            down++
            if (t <= trees[i][y]) break
        }
        for (j in y - 1 downTo 0) {
            left++
            if (t <= trees[x][j]) break
        }
        for (j in y + 1 until size) {
            right++
            if (t <= trees[x][j]) break
        }

        return up * down * left * right
    }
}