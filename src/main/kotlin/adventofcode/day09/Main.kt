package adventofcode.day09

fun main() {
    val grid = """
        ......
        ......
        ......
        ......
        H.....
    """.trimIndent()

    val commands = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()
        .split(System.lineSeparator())
        .map { row -> row.split(" ") }

    commands.forEach { println(it) }
}
