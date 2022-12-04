package adventofcode.day03

import java.io.*

fun main() {
    val score = mutableMapOf<Char, Int>()
    CharRange('a', 'z').forEachIndexed { idx, c -> score[c] = idx + 1 }
    IntRange(27, 52).forEachIndexed { idx, nr -> score['A' + idx] = nr }

    val input1 = File("requirements/day03/input.txt")
        .useLines { it.toList() }
    val sumOfPriorities1 = part1(input1, score)
    println("Part 1 sum of priorities: $sumOfPriorities1")

    val input2 = File("requirements/day03/input_part2.txt")
        .useLines { it.toList() }
    val sumOfPriorities2 = part2(input2, score)
    println("Part 2 sum of priorities: $sumOfPriorities2")
}

private fun part1(input1: List<String>, score: MutableMap<Char, Int>): Int =
    input1.map { it.chunked(it.length / 2) }
        .map { it.map { it.toList() } }
        .flatMap { it[0].intersect(it[1]) }
        .map { score[it] }
        .sumOf { it!! }

private fun part2(input1: List<String>, score: MutableMap<Char, Int>): Int =
    input1.chunked(3)
        .map { it.map { it.toList() } }
        .flatMap { it[0].intersect(it[1]).intersect(it[2]) }
        .map { score[it] }
        .sumOf { it!! }
