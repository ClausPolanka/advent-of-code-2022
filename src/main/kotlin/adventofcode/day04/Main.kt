package adventofcode.day04

import java.io.*

fun main() {
    val input = File("requirements/day04/input.txt")
        .useLines { it.toList() }
    println("Part 1 Assignment Pairs where one range fully contains the other: "
            + part1(input))
    println("Part 2 Assignment pairs where ranges overlap: "
            + part2(input))
}

private fun part1(input1: List<String>) = input1
    .map { it.split(",") }
    .map {
        it.map {
            IntRange(
                it.split("-")[0].toInt(),
                it.split("-")[1].toInt())
        }
    }.map { it.map { it.toList() } }
    .map {
        listOf(it[0].containsAll(it[1]), it[1].containsAll(it[0]))
    }.filter { it.contains(true) }
    .size

private fun part2(input1: List<String>) = input1
    .map { it.split(",") }
    .map {
        it.map {
            IntRange(
                it.split("-")[0].toInt(),
                it.split("-")[1].toInt())
        }
    }.map { it.map { it.toList() } }
    .map {
        it[0].intersect(it[1])
    }.filter { it.isNotEmpty() }
    .size
