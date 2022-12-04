package tests.adventofcode.day01

import adventofcode.day01.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.lang.System.lineSeparator

class Tests {

    @Test
    fun calculateMaxForSampleInput() {
        val input = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
        """.trimIndent().split(lineSeparator())
        val max = maxOf(input)
        assertEquals(24000, max)
    }

}
