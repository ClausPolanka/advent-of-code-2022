package adventofcode.day04

import java.io.*

fun main() {
    val input1 = File("requirements/day04/input.txt")
        .useLines { it.toList() }
    println("Part 1 Assignment Pairs where one range fully contains the other: "
            + part1(input1))
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
