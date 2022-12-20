package adventofcode.day09

import kotlin.math.*

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

    var head = Pair(0, 0)
    var tail = Pair(0, 0)


    // R4
    IntRange(1, 4).forEach {
        head = Pair(head.first, it)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }

    // L4
    IntRange(1, 4).reversed().forEach {
        head = Pair(head.first, it)
        println("H: $head")
        tail = updateTail(tail, head)
        println("T: $tail")
    }
}

fun updateTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {

    // Do nothing when distance is max 1
    if (abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1) {
        return tail
    }

    // Move R
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second > tail.second) {
        return Pair(tail.first, tail.second + 1)
    }

    // Move L
    if (head.first == tail.first && abs(head.second - tail.second) > 1 && head.second < tail.second) {
        return Pair(tail.first, tail.second - 1)
    }

    return Pair(-1, -1)
}
