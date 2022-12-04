package adventofcode.day03

import java.io.*

fun main() {
    val input1 = File("requirements/day03/input.txt")
        .useLines { it.toList() }
    val score = mutableMapOf<Char, Int>()
    CharRange('a', 'z').forEachIndexed { idx, c -> score[c] = idx + 1 }
    IntRange(27, 52).forEachIndexed { idx, nr -> score['A' + idx] = nr }
    val sumOfPriorities = input1.map { it.chunked(it.length / 2) }
        .map { it.map { it.toList() } }
        .flatMap { it[0].intersect(it[1]) }
        .map { score[it] }
        .sumOf { it!! }
    println("Part 1 sum of priorities: $sumOfPriorities")
}
