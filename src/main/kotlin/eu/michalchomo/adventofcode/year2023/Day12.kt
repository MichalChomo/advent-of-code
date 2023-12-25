package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day12 : Day {

    override val number: Int = 12

    override fun part1(input: List<String>): String = input.sumOf { processLine(it) }.toString()

    override fun part2(input: List<String>): String = input.sumOf { line ->
        val a = processLine(line)
        val a2 = processLine(line.unfold(2))
        val a3 = a2 / a * a2
        val a4 = a3 / a2 * a3
        a4 / a3 * a4
    }.toString()

    private fun processLine(line: String): Int {
        val damagedGroupsSizes = line.split(' ')[1].split(',')
            .map { it.toInt() }
        return line.split(' ')[0].variants(damagedGroupsSizes)
            .map { ".${it.joinToString("")}." }
            .count {
                damagedGroupsSizes.fold(it) { acc, damagedCount ->
                    val damagedGroup = ".${"#".repeat(damagedCount)}."
                    val firstDamaged = acc.indexOf('#')
                    val i = acc.indexOf(damagedGroup)
                    if (i == -1 || i >= firstDamaged) {
                        return@count false
                    }
                    val newStartIndex = i + damagedGroup.length - 1
                    if (newStartIndex < acc.length) acc.substring(newStartIndex) else ""
                }
                true
            }
    }

    private fun CharArray.countSymbol(symbol: Char): Int = this.count { it == symbol }

    private fun String.variants(damagedGroupsSizes: List<Int>): Set<CharArray> {
        val indices = this.withIndex().filter { it.value == '?' }.map { it.index }
        val alphabet = charArrayOf('.', '#')
        val variants = mutableSetOf<CharArray>()
        val maxGroupSize = damagedGroupsSizes.max()
        val damagedSpringsCount = damagedGroupsSizes.sum()
        loop@ for (i in 0..<(1L shl indices.size)) {
            val variant = this.toCharArray()
            for (j in indices.indices) {
                if (variant.countSymbol('#') > damagedSpringsCount
                    || variant.containsContiguousGroupOfSize(maxGroupSize+1)) {
                    continue@loop
                }
                val addedSymbol = alphabet[((i shr j) and 1).toInt()]
                variant[indices[j]] = addedSymbol
            }
            if (variant.countSymbol('#') != damagedSpringsCount) continue@loop
            variants.add(variant)
        }
        return variants
    }

    private fun CharArray.containsContiguousGroupOfSize(groupSize: Int): Boolean {
        this.fold(0) { acc, c ->
            if (acc == groupSize) return true
            if (c == '#') {
                 acc + 1
            } else 0
        }
        return false
    }

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
