package adventofcode.day11

import java.lang.System.lineSeparator

fun main() {
    println("hello world")
    val s = """
        Monkey 0:
          Starting items: 78, 53, 89, 51, 52, 59, 58, 85
          Operation: new = old * 3
          Test: divisible by 5
            If true: throw to monkey 2
            If false: throw to monkey 7

        Monkey 1:
          Starting items: 64
          Operation: new = old + 7
          Test: divisible by 2
            If true: throw to monkey 3
            If false: throw to monkey 6
        
        Monkey 2:
          Starting items: 71, 93, 65, 82
          Operation: new = old + 5
          Test: divisible by 13
            If true: throw to monkey 5
            If false: throw to monkey 4
    """.trimIndent()
    val ms: List<Monkey> = parseMonkeys(s)
    ms.forEach {
        println(it)
    }
}

fun parseMonkeys(s: String): List<Monkey> {
    return s.split("${lineSeparator()}${lineSeparator()}")
        .map { parseMonkey(it) }
}

fun parseMonkey(s: String): Monkey {
    if (s.isEmpty()) {
        return defaultMonkey
    }
    val lines = s.split(lineSeparator())
    return defaultMonkey.copy(
        id = parseMonkeyId(lines[0]),
        startingItems = parseStartingItems(lines[1]),
        fn = parseOperation(lines[2]),
        divisor = parseDivisor(lines[3]),
        trueMonkeyId = parseTrueMonkeyId(lines[4]),
        falseMonkeyId = parseFalseMonkeyId(lines[5]),
    )
}

fun parseMonkeyId(s: String): Int = s.split(" ")[1].split(":").first().toInt()

fun parseStartingItems(s: String): List<Int> = s.split(":")[1].split(",")
    .map { it.trim() }
    .map { it.toInt() }

fun parseOperation(s: String): Operation {
    val op = s.split("=")[1].trim()
    if (op.contains("*")) {
        val left = op.split("*")[0].trim()
        val right = op.split("*")[1].trim()
        return Operation(left, right, "*")
    }
    if (op.contains("+")) {
        val left = op.split("+")[0].trim()
        val right = op.split("+")[1].trim()
        return Operation(left, right, "+")
    }
    if (op.contains("-")) {
        val left = op.split("-")[0].trim()
        val right = op.split("-")[1].trim()
        return Operation(left, right, "-")
    }
    if (op.contains("/")) {
        val left = op.split("/")[0].trim()
        val right = op.split("/")[1].trim()
        return Operation(left, right, "/")
    }
    throw IllegalArgumentException("Operation not supported: '$op'")
}

fun parseDivisor(s: String): Int = parseLastNumberOf(s)

fun parseTrueMonkeyId(s: String): Int = parseLastNumberOf(s)

fun parseFalseMonkeyId(s: String): Int = parseLastNumberOf(s)

private fun parseLastNumberOf(s: String) = s.split(" ").last().toInt()

data class Monkey(
    val id: Int,
    val startingItems: List<Int>,
    val fn: Operation,
    val divisor: Int,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int)

val defaultMonkey = Monkey(
    id = -1,
    startingItems = emptyList(),
    fn = Operation("x", "y", "+"),
    divisor = 0,
    trueMonkeyId = -1,
    falseMonkeyId = -1)

data class Operation(val left: String, val right: String, val operand: String)
