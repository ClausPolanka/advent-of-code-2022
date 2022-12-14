package tests.adventofcode.day11

import adventofcode.day11.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.*
import java.math.*

class Tests {
    @Test
    fun `parse monkey returns default monkey for empty string`() {
        val m = parseMonkey("")
        assertEquals(
            defaultMonkey,
            m,
            "monkey")
    }

    @Test
    fun `parse monkey returns monkey for input string`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()
        val m = parseMonkey(s)
        // @formatter:off
        assertAll(
            { assertEquals(0, m.id, "monkey id") },
            { assertEquals(listOf(
                79.toBigInteger(),
                98.toBigInteger()
            ), m.startingItems, "monkey starting items") },
            { assertEquals(
                Operation("old", "19", "*"), m.fn, "monkey operation") },
            { assertEquals(23, m.divisor, "monkey divisor") },
            { assertEquals(2, m.trueMonkeyId, "monkey id if true") },
            { assertEquals(3, m.falseMonkeyId, "monkey id if false") },
        )
        // @formatter:on
    }

    @Test
    fun `monkey throws items`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()
        val m = parseMonkey(s)
        val monkeys = m.throwItems()
        // @formatter:off
        assertAll(
            { assertEquals(
                Pair(3, 500.toBigInteger()),
                monkeys.first(),
                "monkey") },
            { assertEquals(
                Pair(3, 620.toBigInteger()),
                monkeys.last(),
                "monkey") },
        )
        // @formatter:on
    }

    @Test
    fun `monkeys throw items`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()
        val ms = parseMonkeys(s)
        val ms1 = ms[0].throwItems()
        assertEquals(listOf(
            Pair(3, 500.toBigInteger()),
            Pair(3, 620.toBigInteger())
        ), ms1, "monkeys")
        val ms2 = ms[1].throwItems()
        assertEquals(listOf(
            Pair(0, 20.toBigInteger()),
            Pair(0, 23.toBigInteger()),
            Pair(0, 27.toBigInteger()),
            Pair(0, 26.toBigInteger()),
        ), ms2, "monkeys")
        val ms3 = ms[2].throwItems()
        assertEquals(listOf(
            Pair(1, 2080.toBigInteger()),
            Pair(3, 1200.toBigInteger()),
            Pair(3, 3136.toBigInteger()),
        ), ms3, "monkeys")
        val ms4 = ms[3].throwItems()
        assertEquals(listOf(
            Pair(1, 25.toBigInteger())
        ), ms4, "monkeys")
    }

    @Test
    fun `one round of monkeys throwing items`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()
        val ms = parseMonkeys(s)
        oneRoundOfThrowing(ms)
        // @formatter:off
        assertAll(
            { assertEquals(listOf(
                    20.toBigInteger(),
                    23.toBigInteger(),
                    27.toBigInteger(),
                    26.toBigInteger()),
                ms.first().startingItems, "monkey items") },
            { assertEquals(listOf(
                2080.toBigInteger(),
                25.toBigInteger(),
                167.toBigInteger(),
                207.toBigInteger(),
                401.toBigInteger(),
                1046.toBigInteger()),
                ms[1].startingItems, "monkey items") },
            { assertEquals(
                emptyList<BigDecimal>(),
                ms[2].startingItems, "monkey items") },
            { assertEquals(
                emptyList<BigDecimal>(),
                ms[3].startingItems, "monkey items") },
        )
        // @formatter:on
    }

    @Test
    fun `20 rounds of monkeys throwing items`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()
        val ms = parseMonkeys(s)
        repeat(20) { oneRoundOfThrowing(ms) }
        // @formatter:off
        assertAll(
            { assertEquals(listOf(
                10.toBigInteger(),
                12.toBigInteger(),
                14.toBigInteger(),
                26.toBigInteger(),
                34.toBigInteger()),
                ms.first().startingItems, "monkey items") },
            { assertEquals(listOf(
                245.toBigInteger(),
                93.toBigInteger(),
                53.toBigInteger(),
                199.toBigInteger(),
                115.toBigInteger()),
                ms[1].startingItems, "monkey items") },
            { assertEquals(
                emptyList<BigDecimal>(),
                ms[2].startingItems, "monkey items") },
            { assertEquals(
                emptyList<BigDecimal>(),
                ms[3].startingItems, "monkey items") },
        )
        // @formatter:on
    }

    @Test
    fun `total number of times each monkey inspects items after 20 rounds`() {
        val s = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()
        val ms = parseMonkeys(s)
        repeat(20) { oneRoundOfThrowing(ms) }
        // @formatter:off
        assertAll(
            { assertEquals(
                101L,
                ms.first().inspectedItems, "monkey inspected items") },
            { assertEquals(
                95L,
                ms[1].inspectedItems, "monkey inspected items") },
            { assertEquals(
                7L,
                ms[2].inspectedItems, "monkey inspected items") },
            { assertEquals(
                105L,
                ms[3].inspectedItems, "monkey inspected items") },
        )
        // @formatter:on
    }

    @Test
    fun `monkey business for input file`() {
        val input =
            File("requirements/day11/input.txt").readText()
        val mb = monkeyBusinessFor(input)
        assertEquals(50616L, mb, "monkey business")
    }

    @Test
    fun `monkey business for sample`() {
        val input =
            File("requirements/day11/sample.txt").readText()
        val mb = monkeyBusinessFor(input)
        assertEquals(10605L, mb, "monkey business")
    }

}
