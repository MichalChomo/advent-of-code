package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.CharMatrix
import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.split
import eu.michalchomo.adventofcode.toCharMatrix
import eu.michalchomo.adventofcode.transpose

object Day13 : Day {

    override val number: Int = 13

    override fun part1(input: List<String>): String = input.split { it.isEmpty() }.sumOf { pattern ->
        val m = pattern.toCharMatrix()
        val vertical = m.transpose().findReflection()?.reflectionScore(multiplier = 100)
        if (vertical != null) return@sumOf vertical
        m.findReflection()?.reflectionScore() ?: 0
    }.toString()

    override fun part2(input: List<String>): String = input.split { it.isEmpty() }
        .sumOf { pattern ->
            val m = pattern.toCharMatrix()
            val originalHorizontal = m.findReflection()
            val originalVertical = m.transpose().findReflection()
            var reflection = 0
            loop@ for (i in pattern.indices) for (j in pattern[0].indices) {
                m.swapChar(i, j, '#', '.')
                val vertical = m.transpose().findAllReflections().firstOrNull { it != originalVertical }
                if (vertical != null) {
                    reflection = vertical.reflectionScore(multiplier = 100)
                    break@loop
                }
                val horizontal = m.findAllReflections().firstOrNull { it != originalHorizontal }
                if (horizontal != null) {
                    reflection = horizontal.reflectionScore()
                    break@loop
                }
                m.swapChar(i, j, '#', '.')
            }
            reflection
        }.toString()

    private fun CharArray.isPalindrome(): Boolean =
        this.size > 1 && this.size % 2 == 0 && this.contentEquals(this.reversed().toCharArray())

    private fun CharArray.findPalindromes(): Set<IntRange> =
        this.indices.flatMap { i ->
            (this.size downTo i).mapNotNull { j ->
                if (this.joinToString("").substring(i, j).toCharArray().isPalindrome()) {
                    i..<j
                } else null
            }
        }.filter { it.first == 0 || it.last == this.size - 1 }.toSet()

    private fun CharMatrix.findPalindromes(): List<IntRange> = this.flatMap { line -> line.findPalindromes() }
        .groupBy { it }.values.maxByOrNull { it.size }!!

    private fun CharMatrix.findAllReflections(): Set<IntRange> =
        this.flatMap { line -> line.findPalindromes() }
            .groupBy { it }.values
            .filter { isReflection(it, this) }
            .map { it.first() }
            .toSet()

    private fun CharMatrix.findReflection(): IntRange? = this.findPalindromes().let { palindromes ->
        if (isReflection(palindromes, this)) {
            palindromes.firstOrNull()
        } else null
    }

    private fun IntRange.reflectionScore(multiplier: Int = 1): Int =
        (((this.last + 1) - this.first) / 2 + this.first) * multiplier

    private fun isReflection(palindromes: List<IntRange?>, pattern: CharMatrix) =
        palindromes.size == pattern.size && palindromes.toSet().size == 1

    private fun CharMatrix.swapChar(i: Int, j: Int, c1: Char, c2: Char) {
        this[i][j] = when (this[i][j]) {
            c1 -> c2
            else -> c1
        }
    }

}

fun main() {
    main(Day13)
}
