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
                    add(Pair("$it, 1/2 ${idx + 1}", 0))
                    add(Pair("$it, 2/2 ${idx + 1}", parts.last().toInt()))
                }
            }
        }
    }
    var sum = 0
    var i = 20
    while (i < result.size) {
        val todo = result.take(i)
        val tmp = todo.sumOf { it.second } +
                if (todo.last().first.contains(
                        "noop") || todo.last().first.contains("1/2")) 1 else 0
        sum += (i * tmp)
        i += 40
    }
    println(sum)
}
