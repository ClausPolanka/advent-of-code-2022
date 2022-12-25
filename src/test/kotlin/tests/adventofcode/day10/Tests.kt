package tests.adventofcode.day10

import adventofcode.day10.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

class Tests {
    private val mod40 = { currentCylce: Int -> currentCylce % 40 == 20 }

    @Test
    fun `noop, cycle == 1`() {
        val result = part1WorkingSolution(
            commands = listOf("noop")) { cycle -> cycle == 1 }
        assertEquals(
            Data(signalStrength = 0, x = 1, currentCycle = 2),
            result)
    }

    @Test
    fun `noop, cycle == 2`() {
        val result = part1WorkingSolution(
            commands = listOf("noop")) { cycle -> cycle == 2 }
        assertEquals(
            Data(signalStrength = 2, x = 1, currentCycle = 2),
            result)
    }

    @Test
    fun `addX 1, cycle == 1`() {
        val result =
            part1WorkingSolution(listOf("addX 1")) { cycle -> cycle == 1 }
        assertEquals(
            Data(signalStrength = 0, x = 2, currentCycle = 3),
            result)
    }

    @Test
    fun `addX 1, cycle == 2`() {
        val result =
            part1WorkingSolution(listOf("addX 1")) { cycle -> cycle == 2 }
        assertEquals(
            Data(signalStrength = 2, x = 2, currentCycle = 3),
            result)
    }

    @Test
    fun `addX 1, cycle == 3`() {
        val result =
            part1WorkingSolution(listOf("addX 1")) { cycle -> cycle == 3 }
        assertEquals(
            Data(signalStrength = 6, x = 2, currentCycle = 3),
            result)
    }

    @Test
    fun `addX 1, addX 1, cycle == 1`() {
        val result = part1WorkingSolution(listOf(
            "addX 1",
            "addX 1")) { cycle -> cycle == 1 }
        assertEquals(
            Data(signalStrength = 0, x = 3, currentCycle = 5),
            result)
    }

    @Test
    fun `addX 1, addX 1, cycle == 2`() {
        val result =
            part1WorkingSolution(listOf("addX 1", "addX 1")
            ) { cycle -> cycle == 2 }
        assertEquals(
            Data(signalStrength = 2, x = 3, currentCycle = 5),
            result)
    }

    @Test
    fun `addX 1, addX 1, cycle == 1 || 2`() {
        val result =
            part1WorkingSolution(listOf("addX 1", "addX 1")
            ) { cycle -> cycle == 1 || cycle == 2 }
        assertEquals(
            Data(signalStrength = 2, x = 3, currentCycle = 5),
            result)
    }

    @Test
    fun `noop, cycle == mod 40`() {
        val result = part1WorkingSolution(listOf("noop"), mod40)
        assertEquals(
            Data(signalStrength = 0, x = 1, currentCycle = 2),
            result)
    }

    @Test
    fun `addX 1, cylce == mod 40`() {
        val result = part1WorkingSolution(listOf("addX 1"), mod40)
        assertEquals(
            Data(signalStrength = 0, x = 2, currentCycle = 3),
            result)
    }
}
