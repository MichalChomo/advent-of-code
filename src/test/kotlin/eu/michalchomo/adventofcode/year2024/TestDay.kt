package eu.michalchomo.adventofcode.year2024

import eu.michalchomo.adventofcode.Day
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class TestDay {

    companion object {
        @JvmStatic
        fun testAllDays() = listOf(Day01)
    }

    @ParameterizedTest
    @MethodSource
    fun testAllDays(day: Day) {
        val input = readInputLines(day.name())
        val answers = readInputLines("${day.name()}_answers")
        assertEquals(2, answers.size, "There should be 2 answers")
        assertEquals(answers[0], day.part1(input), "Part 1 is incorrect")
        assertEquals(answers[1], day.part2(input), "Part 2 is incorrect")
    }
}
