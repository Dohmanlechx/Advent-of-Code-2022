import reader.Reader

fun main() {
    val input: List<String> = Reader.input(1)
    val example: List<String> = Reader.example(1)

    input.forEach(::println)
    example.forEach(::println)
}