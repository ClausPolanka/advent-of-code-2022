package adventofcode.day03

import java.io.*

fun main() {
    val scoreMap = mutableMapOf<Char, Int>()
    CharRange('a', 'z').forEachIndexed { idx, c -> scoreMap[c] = idx + 1 }
    IntRange(27, 52).forEachIndexed { idx, nr -> scoreMap['A' + idx] = nr }

    val input1 = File("requirements/day03/input.txt")
        .useLines { it.toList() }
    val sumOfPriorities1 = part1(input1, scoreMap)
    println("Part 1 sum of priorities: $sumOfPriorities1")

    val input2 = File("requirements/day03/input_part2.txt")
        .useLines { it.toList() }
    val sumOfPriorities2 = part2(input2, scoreMap)
    println("Part 2 sum of priorities: $sumOfPriorities2")
}

private fun part1(
    rucksacks: List<String>,
    scoreMap: MutableMap<Char, Int>): Int =
    rucksacks.map { rucksack -> rucksack.chunked(rucksack.length / 2) }
        .map { compartments -> compartments.map { c -> c.toList() } }
        .flatMap { compartment ->
            compartment[0].intersect(compartment[1].toSet())
        }
        .map { item -> scoreMap[item] }
        .sumOf { score -> score!! }

private fun part2(
    rucksacks: List<String>,
    scoreMap: MutableMap<Char, Int>): Int =
    rucksacks.chunked(3)
        .map { rucksackGroup ->
            rucksackGroup.map { rucksack -> rucksack.toList() }
        }
        .flatMap { rucksack ->
            rucksack[0]
                .intersect(rucksack[1].toSet())
                .intersect(rucksack[2].toSet())
        }
        .map { item -> scoreMap[item] }
        .sumOf { score -> score!! }
