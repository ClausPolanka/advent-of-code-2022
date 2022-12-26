package adventofcode.day11

fun main() {
    println("hello world")
    val s = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
    """.trimIndent()
    val m: Monkey = parseMonkey(s)
}

fun parseMonkey(s: String): Monkey {
    if (s.isEmpty()) {
        return defaultMonkey
    }
    val lines = s.split(System.lineSeparator())
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
    return Operation("left", "right", "-")
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

data class Operation(val left: String, val right: String, val s: String)
