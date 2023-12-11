package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class TestDay {

    companion object {
        @JvmStatic
        fun testAllDays() = listOf(Day01, Day02, Day03, Day04, Day05, Day06, Day07, Day08, Day09, Day10, Day11)
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
