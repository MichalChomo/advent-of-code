package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.intersect
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.removeIntersectWith
import eu.michalchomo.adventofcode.split

object Day05 : Day {

    override val number: Int = 5

    override fun part1(input: List<String>): Int = input.split { line -> line.isEmpty() || !line[0].isDigit() }
        .fold(mutableListOf<MutableList<Long>>().apply {
            val seeds = input[0].split(": ", " ")
            seeds.slice(1..<seeds.size).map { it.toLong() }.forEach { seed ->
                this.add(mutableListOf(seed))
            }
        }) { destinationLists, category ->
            val sourceRangesToDestinationStarts = category.toSourceRangesToDestinationStarts()
            destinationLists.forEach { destinations ->
                val last = destinations.last()
                val sourceRange = sourceRangesToDestinationStarts.keys.find { last in it }
                if (sourceRange != null) {
                    val destinationStart = sourceRangesToDestinationStarts[sourceRange]!!
                    destinations.add(destinationStart + last - sourceRange.first)
                } else {
                    destinations.add(last)
                }
            }
            destinationLists
        }.minOf { it.lastOrNull() ?: Long.MAX_VALUE }.toInt()

    override fun part2(input: List<String>): Int = input.split { line -> line.isEmpty() || !line[0].isDigit() }
        .fold(mutableListOf<MutableList<List<LongRange>>>().apply {
            val seeds = input[0].split(": ", " ")
            seeds.slice(1..<seeds.size).chunked(2).map { range ->
                val rangeStart = range[0].toLong()
                val rangeLength = range[1].toLong()
                this.add(mutableListOf(listOf(rangeStart..<(rangeStart + rangeLength))))
            }
        }) { destinationRangeLists, category ->
            val sourceRangesToDestinationStarts = category.toSourceRangesToDestinationStarts()
            destinationRangeLists.forEach { destinationRanges ->
                val currentSources = destinationRanges.last()
                destinationRanges.add(
                    currentSources.fold(currentSources to mutableListOf<List<LongRange>>()) { acc, currentRange ->
                        var newNotMapped = acc.first
                        sourceRangesToDestinationStarts.keys.forEach { sourceRange ->
                            newNotMapped.mapNotNull { it.intersect(sourceRange) }.forEach { intersect ->
                                if (intersect.first < intersect.last) {
                                    newNotMapped = newNotMapped.map { it.removeIntersectWith(intersect) }.flatten()
                                    val destinationStart = sourceRangesToDestinationStarts[sourceRange]!!
                                    val offset = destinationStart - sourceRange.first
                                    acc.second.add(listOf((intersect.first + offset)..(intersect.last + offset)))
                                }
                            }
                        }
                        newNotMapped to acc.second
                    }.let { it.first + it.second.flatten() }
                )
            }
            destinationRangeLists
        }.minOf { it.last().minOf { it.first } }.toInt()

    private fun List<String>.toSourceRangesToDestinationStarts() = this.associate { line ->
        val split = line.split(' ')
        val destinationStart = split[0].toLong()
        val sourceStart = split[1].toLong()
        val rangeLength = split[2].toLong()
        val sourceRange = sourceStart..<(sourceStart + rangeLength)
        sourceRange to destinationStart
    }

}

fun main() {
    main(Day05)
}
