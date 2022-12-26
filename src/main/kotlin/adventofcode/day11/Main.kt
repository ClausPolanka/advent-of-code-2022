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
    lines.forEach {
        println(it)
    }
    val monkeyId: Int = parseMonkeyId(lines[0])
    val startingItems: List<Int> = parseStartingItems(lines[1])
    val operation: Operation = parseOperation(lines[2])
    return defaultMonkey.copy(
        id = monkeyId,
        startingItems = startingItems,
        fn = operation
    )
}

fun parseOperation(s: String): Operation {
    val op = s.split("=")[1].trim()
    if (op.contains("*")) {
        val left = op.split("*")[0].trim()
        val right = op.split("*")[1].trim()
        return Operation(left, right, "*")
    }
    return Operation("left", "right", "-")
}

data class Operation(val left: String, val right: String, val s: String)

fun parseMonkeyId(s: String): Int {
    return s.split(" ")[1].split(":").first().toInt()
}

fun parseStartingItems(s: String): List<Int> {
    return s.split(":")[1].split(",")
        .map { it.trim() }
        .map { it.toInt() }
}

data class Monkey(
    val id: Int,
    val startingItems: List<Int>,
    val fn: Operation,
    val predicate: (Int, Int) -> Boolean,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int)

val defaultMonkey = Monkey(
    id = -1,
    startingItems = emptyList(),
    fn = Operation("x", "y", "+"),
    predicate = { _, _ -> false },
    trueMonkeyId = -1,
    falseMonkeyId = -1)
