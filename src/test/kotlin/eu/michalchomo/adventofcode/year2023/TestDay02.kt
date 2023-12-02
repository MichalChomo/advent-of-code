package eu.michalchomo.adventofcode.year2023

import kotlin.test.Test
import kotlin.test.assertEquals

class TestDay02 {
    @Test
    fun testDay02() {
        val input = readInputLines("Day02")
        val results = readInputLines("Day02_results")
        assertEquals(results[0].toInt(), part1(input))
        assertEquals(results[1].toInt(), part2(input))
    }
}
