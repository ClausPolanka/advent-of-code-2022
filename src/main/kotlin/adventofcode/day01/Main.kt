package adventofcode.day01

import java.io.*

fun main() {
    val input1 = File("requirements/day01/input.txt")
        .useLines { it.toList() }
    val maxElfCalories = part1(input1)
    println("Part 1 Max Elf Calories: $maxElfCalories")
    val input2 = File("requirements/day01/input_part2.txt")
        .useLines { it.toList() }
    val top3ElfCalories = part2(input2)
    println("Part 2 Top 3 Elf Calories: $top3ElfCalories")
}

fun part1(input: List<String>): Int? {
    return maxOf(caloriesGroupedByElf(input))
}

fun part2(input: List<String>): Int {
    val caloryGroups = caloriesGroupedByElf(input)
    return caloryGroups.map { group -> group.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}

fun caloriesGroupedByElf(input: List<String>): ElfCaloryGroups {
    val allCaloryGroups = mutableListOf<List<Int>>()
    var elf = mutableListOf<Int>()
    input.forEach { calories ->
        if (calories.isNotEmpty()) {
            elf.add(calories.toInt())
        } else {
            allCaloryGroups.add(elf)
            elf = mutableListOf()
        }
        if (calories == input.last() && calories.isNotBlank()) {
            allCaloryGroups.add(elf)
        }
    }
    return allCaloryGroups
}

typealias ElfCaloryGroups = List<List<Int>>

fun maxOf(caloryGroups: ElfCaloryGroups): Int? {
    return caloryGroups.maxOfOrNull { it.sum() }
}
