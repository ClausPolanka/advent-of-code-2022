package adventofcode.day11

import java.lang.System.lineSeparator
import kotlin.math.*

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
    repeat(20) { oneRound(ms) }
}

fun oneRound(ms: List<Monkey>) {
    ms.forEach {
        val items = it.throwItems()
        updateMonkeys(ms, items)
    }
}

fun updateMonkeys(monkeys: List<Monkey>,
                  items: List<Pair<Int, Int>>): List<Monkey> {
    items.forEach { item ->
        val m = monkeys.first { it.id == item.first }
        m.startingItems.add(item.second)
    }
    return monkeys
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

fun parseStartingItems(s: String): MutableList<Int> = s.split(":")[1].split(",")
    .map { it.trim() }
    .map { it.toInt() }
    .toMutableList()

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
    val startingItems: MutableList<Int>,
    val fn: Operation,
    val divisor: Int,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int) {

    fun throwItems(): List<Pair<Int, Int>> {
        val monkeyToWorries = startingItems.map { determineMonkeyFor(it) }
        startingItems.clear()
        return monkeyToWorries
    }

    private fun determineMonkeyFor(worryLevel: Int): Pair<Int, Int> {
        val newWorryLevel = applyOperationFor(worryLevel)
        val finalWorryLevel = monkeyBored(newWorryLevel)
        val mId = if (isDevideableBy(divisor, finalWorryLevel))
            trueMonkeyId else falseMonkeyId
        return Pair(mId, finalWorryLevel)
    }

    private fun isDevideableBy(divisor: Int, value: Int) = value % divisor == 0

    private fun monkeyBored(worryLevel: Int): Int =
        floor(worryLevel.toDouble() / 3).roundToInt()

    private fun applyOperationFor(worryLevel: Int) = when (fn.operand) {
        "+" -> left(worryLevel) + right(worryLevel)
        "*" -> left(worryLevel) * right(worryLevel)
        "/" -> left(worryLevel) / right(worryLevel)
        "-" -> left(worryLevel) - right(worryLevel)
        else -> throw IllegalArgumentException(
            "Operation not supported: '${fn.operand}'")
    }

    private fun left(worryLevel: Int): Int = when (fn.left) {
        "old" -> worryLevel
        else -> fn.left.toInt()
    }

    private fun right(worryLevel: Int): Int = when (fn.right) {
        "old" -> worryLevel
        else -> fn.right.toInt()
    }
}

val defaultMonkey = Monkey(
    id = -1,
    startingItems = mutableListOf(),
    fn = Operation("x", "y", "+"),
    divisor = 0,
    trueMonkeyId = -1,
    falseMonkeyId = -1)

data class Operation(val left: String, val right: String, val operand: String)
