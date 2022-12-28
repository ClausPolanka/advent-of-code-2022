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
                BigDecimal(79),
                BigDecimal(98)
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
                Pair(3, BigDecimal(500)),
                monkeys.first(),
                "monkey") },
            { assertEquals(
                Pair(3, BigDecimal(620)),
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
            Pair(3, BigDecimal(500)),
            Pair(3, BigDecimal(620))
        ), ms1, "monkeys")
        val ms2 = ms[1].throwItems()
        assertEquals(listOf(
            Pair(0, BigDecimal(20)),
            Pair(0, BigDecimal(23)),
            Pair(0, BigDecimal(27)),
            Pair(0, BigDecimal(26)),
        ), ms2, "monkeys")
        val ms3 = ms[2].throwItems()
        assertEquals(listOf(
            Pair(1, BigDecimal(2080)),
            Pair(3, BigDecimal(1200)),
            Pair(3, BigDecimal(3136)),
        ), ms3, "monkeys")
        val ms4 = ms[3].throwItems()
        assertEquals(listOf(
            Pair(1, BigDecimal(25))
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
                    BigDecimal(20),
                    BigDecimal(23),
                    BigDecimal(27),
                    BigDecimal(26)),
                ms.first().startingItems, "monkey items") },
            { assertEquals(listOf(
                BigDecimal(2080),
                BigDecimal(25),
                BigDecimal(167),
                BigDecimal(207),
                BigDecimal(401),
                BigDecimal(1046)),
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
                BigDecimal(10),
                BigDecimal(12),
                BigDecimal(14),
                BigDecimal(26),
                BigDecimal(34)),
                ms.first().startingItems, "monkey items") },
            { assertEquals(listOf(
                BigDecimal(245),
                BigDecimal(93),
                BigDecimal(53),
                BigDecimal(199),
                BigDecimal(115)),
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
                101,
                ms.first().inspectedItems, "monkey inspected items") },
            { assertEquals(
                95,
                ms[1].inspectedItems, "monkey inspected items") },
            { assertEquals(
                7,
                ms[2].inspectedItems, "monkey inspected items") },
            { assertEquals(
                105,
                ms[3].inspectedItems, "monkey inspected items") },
        )
        // @formatter:on
    }

    @Test
    fun `monkey business for input file`() {
        val input =
            File("requirements/day11/input.txt").readText()
        val mb = monkeyBusinessFor(input)
        assertEquals(50616, mb, "monkey business")
    }

    @Test
    fun `monkey business for sample`() {
        val input =
            File("requirements/day11/sample.txt").readText()
        val mb = monkeyBusinessFor(input)
        assertEquals(10605, mb, "monkey business")
    }

}
