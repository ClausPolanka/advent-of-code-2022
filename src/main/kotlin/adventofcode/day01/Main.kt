package adventofcode.day01

import java.io.*

fun main() {
    val input = File("requirements/day01/input.txt").useLines { it.toList() }
    println(maxOf(input))
}

fun maxOf(input: List<String>): Int? {
    val all = mutableListOf<List<Int>>()
    var group = mutableListOf<Int>()
    input.forEach {
        if (it.isNotEmpty()) {
            group.add(it.toInt())
        } else {
            all.add(group)
            group = mutableListOf()
        }
    }
    return all.maxOfOrNull { it.sum() }
}
