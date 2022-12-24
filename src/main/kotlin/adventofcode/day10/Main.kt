package adventofcode.day10

import java.io.*

fun main() {
    val inputFileCommands =
        File("requirements/day10/sample.txt").useLines { it.toList() }
    val commands = inputFileCommands
    val result = buildList {
        commands.forEachIndexed { idx, it ->
            val parts = it.split(" ")
            when (parts.size) {
                1 -> add(Pair("$it, ${idx + 1}", 0))
                else -> {
                    add(Pair("$it, ${idx + 1}", 0))
                    add(Pair("$it, ${idx + 1}", parts.last().toInt()))
                }
            }
        }
    }
    var sum = 0
    var i = 20
    while (i < result.size) {
        val tmp = result.take(i).sumOf { it.second } + 1
        sum += (i * tmp)
        i += 40
    }
    println(sum)
}
