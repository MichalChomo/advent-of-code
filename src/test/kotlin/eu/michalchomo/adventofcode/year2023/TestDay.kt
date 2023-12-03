package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class TestDay {

    companion object {
        @JvmStatic
        fun testDay() = listOf(Day02, Day03)
    }

    @ParameterizedTest
    @MethodSource
    fun testDay(day: Day) {
        val input = readInputLines(day.name())
        val answers = readInputLines("${day.name()}_answers")
        assertEquals(answers[0].toInt(), day.part1(input), "Part 1 is incorrect")
        assertEquals(answers[1].toInt(), day.part2(input), "Part 2 is incorrect")
    }
}
