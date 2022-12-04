package adventofcode.day01

import java.io.*

fun main() {
    val input1 = File("requirements/day01/input.txt")
        .useLines { it.toList() }
    val max = part1(input1)
    println("Result for Part 1: $max")
    val input2 = File("requirements/day01/input_part2.txt")
        .useLines { it.toList() }
    val sumOfTop3 = part2(input2)
    println("Result for Part 2: $sumOfTop3")
}

fun part1(input: List<String>): Int? {
    val grouped = grouped(input)
    return maxOf(grouped)
}

fun part2(input: List<String>): Int {
    val grouped = grouped(input)
    return grouped.map { it.sum() }.sortedDescending().take(3).sum()
}

fun grouped(input: List<String>): MutableList<List<Int>> {
    val all = mutableListOf<List<Int>>()
    var group = mutableListOf<Int>()
    input.forEach {
        if (it.isNotEmpty()) {
            group.add(it.toInt())
        } else {
            all.add(group)
            group = mutableListOf()
        }
        if (it == input.last() && it.isNotBlank()) {
            all.add(group)
        }
    }
    return all
}

fun maxOf(grouped: List<List<Int>>): Int? {
    return grouped.maxOfOrNull { it.sum() }
}
