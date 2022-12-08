package adventofcode.day06

import java.io.*

fun main() {
    val input = File("requirements/day06/input.txt").readText()

    IntRange(0, input.toList().size).forEach { i ->
        val elems = input.drop(i).toList().take(14)
        if (elems.size == elems.distinct().size) {
            println(i + 14)
            return
        }
    }

}
