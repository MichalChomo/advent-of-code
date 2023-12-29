package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.println

object Day12 : Day {

    override val number: Int = 12

    override fun part1(input: List<String>): String = solve(input, unfoldTimes = 1)

    override fun part2(input: List<String>): String = solve(input, unfoldTimes = 5)

    private fun solve(input: List<String>, unfoldTimes: Int = 1): String = input.sumOf { line ->
        cache.clear()
        line.unfold(unfoldTimes).split(' ').let {
            processLineRecursive(it[0], it[1].digitsToIntList(), 0, 0, 0)
        }
    }.toString()

    private val cache = mutableMapOf<Triple<Int, Int, Int>, Long>()
    private fun processLineRecursive(
        springs: String,
        damagedGroupsSizes: List<Int>,
        springIndex: Int,
        groupIndex: Int,
        currentGroupSize: Int
    ): Long {
        val key = Triple(springIndex, groupIndex, currentGroupSize)
        if (key in cache) {
            return cache[Triple(springIndex, groupIndex, currentGroupSize)]!!
        }
        if (springIndex == springs.length) {
            if (groupIndex == damagedGroupsSizes.size && currentGroupSize == 0) return 1
            if (groupIndex == damagedGroupsSizes.size - 1 && damagedGroupsSizes[groupIndex] == currentGroupSize) return 1
            return 0
        }
        var arrangements = 0L
        for (c in charArrayOf('.', '#')) {
            if (springs[springIndex] == c || springs[springIndex] == '?') {
                if (c == '.' && currentGroupSize == 0) {
                    arrangements += processLineRecursive(springs, damagedGroupsSizes, springIndex + 1, groupIndex, 0)
                } else if (c == '.' && currentGroupSize > 0 && groupIndex < damagedGroupsSizes.size && currentGroupSize == damagedGroupsSizes[groupIndex]) {
                    arrangements += processLineRecursive(
                        springs,
                        damagedGroupsSizes,
                        springIndex + 1,
                        groupIndex + 1,
                        0
                    )
                } else if (c == '#') {
                    arrangements += processLineRecursive(
                        springs,
                        damagedGroupsSizes,
                        springIndex + 1,
                        groupIndex,
                        currentGroupSize + 1
                    )
                }
            }
        }
        cache[key] = arrangements
        return arrangements
    }

    private fun String.digitsToIntList(): List<Int> = this.split(',').map { it.toInt() }

    private fun String.unfold(times: Int): String {
        val springsAndGroupSizes = this.split(' ')
        val springs = springsAndGroupSizes[0]
        val groupSizes = springsAndGroupSizes[1]
        return "${springs}?".repeat(times).dropLast(1) + " " + "${groupSizes},".repeat(times).dropLast(1)
    }

}

fun main() {
    main(Day12)
}
