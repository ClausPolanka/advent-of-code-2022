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
    return defaultMoney
}

data class Monkey(
    val startingItems: List<Int>,
    val fn: (Int, Int) -> Int,
    val predicate: (Int, Int) -> Boolean,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int)

val defaultMoney = Monkey(
    startingItems = emptyList(),
    fn = { _, _ -> -1 },
    predicate = { _, _ -> false },
    trueMonkeyId = -1,
    falseMonkeyId = -1)
