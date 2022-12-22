package puzzles

import reader.Reader

private fun main() {
    p1p2()
}

private val input = Reader.input(10)

private fun p1p2() {
    var signalStrengths = 0
    var pixels = ""

    var register = 1
    var cycleCount = 0
    var spritePosition = 0..2

    fun updateSignalStrengths() {
        val sum = listOf(20, 60, 100, 140, 180, 220).find { it == cycleCount }?.let { it * register }
        signalStrengths += sum ?: 0
    }

    fun updatePixels() {
        pixels += if (pixels.length % 40 in spritePosition) "#" else " "
    }

    fun executeCycle() {
        cycleCount++
        updateSignalStrengths()
        updatePixels()
    }

    for (cycle in input) {
        executeCycle()

        if (cycle.startsWith("addx")) {
            executeCycle()
            register += cycle.substringAfter(" ").toInt()
            spritePosition = (register - 1)..(register + 1)
        }
    }

    // Part 1
    require(signalStrengths == 13720)

    // Part 2
    //pixels.chunked(40).forEach(::println)

    // #### ###  #  # ###  #  # ####  ##  #  #
    // #    #  # #  # #  # #  #    # #  # #  #
    // ###  ###  #  # #  # ####   #  #    ####
    // #    #  # #  # ###  #  #  #   #    #  #
    // #    #  # #  # # #  #  # #    #  # #  #
    // #    ###   ##  #  # #  # ####  ##  #  #
}