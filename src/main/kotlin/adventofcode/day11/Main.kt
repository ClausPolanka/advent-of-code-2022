package adventofcode.day11

import java.io.*
import java.lang.System.lineSeparator
import kotlin.math.*

fun main() {
    val input =
        File("requirements/day11/sample.txt").readText()
    val result = monkeyBusinessFor(input)
    println(result)
}

fun monkeyBusinessFor(input: String): Int {
    val monkeys = parseMonkeys(input)
    repeat(20) { oneRoundOfThrowing(monkeys) }
    val sorted = monkeys.sortedByDescending { it.inspectedItems }
    return sorted.first().inspectedItems * sorted[1].inspectedItems
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
    return Monkey(
        id = parseMonkeyId(lines[0]),
        startingItems = parseStartingItems(lines[1]),
        fn = parseOperation(lines[2]),
        divisor = parseDivisor(lines[3]),
        trueMonkeyId = parseTrueMonkeyId(lines[4]),
        falseMonkeyId = parseFalseMonkeyId(lines[5]))
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

fun oneRoundOfThrowing(monkeys: List<Monkey>) {
    monkeys.forEach { monkey ->
        val items = monkey.throwItems()
        updateMonkeys(monkeys, items)
    }
}

fun updateMonkeys(
    monkeys: List<Monkey>,
    monkeyIdToNewItem: List<Pair<Int, Int>>): List<Monkey> {

    monkeyIdToNewItem.forEach {
        val m = monkeys.first { monkey -> monkey.id == it.first }
        m.startingItems.add(it.second)
    }
    return monkeys
}

data class Monkey(
    val id: Int,
    val startingItems: MutableList<Int>,
    val fn: Operation,
    val divisor: Int,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int) {

    var inspectedItems: Int = 0

    fun throwItems(): List<Pair<Int, Int>> {
        inspectedItems += startingItems.size
        val monkeyToWorries = startingItems.map { determineMonkeyFor(it) }
        startingItems.clear()
        return monkeyToWorries
    }

    private fun determineMonkeyFor(worryLevel: Int): Pair<Int, Int> {
        val newWorryLevel = applyOperationFor(worryLevel)
        val finalWorryLevel = monkeyBored(newWorryLevel)
        val monkeyId = if (isDevideableBy(divisor, finalWorryLevel))
            trueMonkeyId
        else
            falseMonkeyId
        return Pair(monkeyId, finalWorryLevel)
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
