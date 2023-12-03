package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class TestDay {

    companion object {
        @JvmStatic
        fun testDay() = listOf(Day02)
    }

    @ParameterizedTest
    @MethodSource
    fun testDay(day: Day) {
        val input = readInputLines(day.name())
        val answers = readInputLines("${day.name()}_answers")
        assertEquals(answers[0].toInt(), day.part1(input))
        assertEquals(answers[1].toInt(), day.part2(input))
    }
}
