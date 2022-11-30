package reader

object Reader {
    fun input(day: Int, split: String = "\n") =
        lines("/$day/input", split)

    fun example(day: Int, split: String = "\n") =
        lines("/$day/example", split)

    private fun lines(path: String, split: String) =
        this@Reader::class.java
            .getResource(path)!!
            .readText()
            .split(split)
            .map(String::trim)
}