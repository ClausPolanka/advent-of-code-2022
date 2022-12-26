package tests.adventofcode.day11

import adventofcode.day11.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

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
              Operation: new = 1 / old
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()
        val m = parseMonkey(s)
        // @formatter:off
        assertAll(
            { assertEquals(0, m.id, "monkey id") },
            { assertEquals(
                    listOf(79, 98), m.startingItems, "monkey starting items") },
            { assertEquals(
                Operation("1", "old", "/"), m.fn, "monkey operation") },
            { assertEquals(23, m.divisor, "monkey divisor") },
            { assertEquals(2, m.trueMonkeyId, "monkey id if true") },
            { assertEquals(3, m.falseMonkeyId, "monkey id if false") },
        )
        // @formatter:on
    }

}
