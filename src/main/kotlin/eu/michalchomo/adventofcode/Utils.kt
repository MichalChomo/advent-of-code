package eu.michalchomo.adventofcode

import java.io.File

private const val ROOT_PATH = "src/main/kotlin/eu/michalchomo/adventofcode/year"

fun readInput(year: Int, name: String) =
    File("$ROOT_PATH$year", "$name.txt").readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(year: Int, name: String) =
    File("$ROOT_PATH$year", "$name.txt").readLines()

fun linesAsInts(lines: List<String>) = lines.map { l -> l.toInt() }

fun Boolean.toInt() = if (this) 1 else 0

fun <T> List<T>.split(includeEmpty: Boolean = false, isDelimiter: (T) -> Boolean) =
    this.foldIndexed(mutableListOf<List<T>>() to mutableListOf<T>()) { index, (allParts, currentPart), element ->
        if (isDelimiter(element) || index == this.size - 1) {
            if (includeEmpty || currentPart.isNotEmpty()) {
                if (index == this.size - 1) currentPart.add(element)
                allParts.add(currentPart.toList())
            }
            currentPart.clear()
        } else {
            currentPart.add(element)
        }
        allParts to currentPart
    }.first.toList()

fun LongRange.intersect(other: LongRange): LongRange? {
    val start = maxOf(this.first, other.first)
    val endInclusive = minOf(this.last, other.last)
    return if (start <= endInclusive) start..endInclusive else null
}

fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last

fun LongRange.removeIntersectWith(other: LongRange): List<LongRange> = this.intersect(other)?.let { intersect ->
    if (intersect.first == this.first && intersect.last == this.last) {
        emptyList()
    } else if (intersect.first > this.first && intersect.last < this.last) {
        listOf(
            this.first..<intersect.first,
            intersect.last + 1..this.last
        )
    } else {
        if (intersect.first > this.first) {
            listOf(this.first..<intersect.first)
        } else {
            listOf(intersect.last+1..this.last)
        }
    }
} ?: listOf(this)

typealias CharMatrix = Array<CharArray>
fun List<String>.toCharMatrix(): CharMatrix = this.map { it.toCharArray() }.toTypedArray()
fun CharMatrix.transpose(): CharMatrix = this[0].indices.map { colIndex ->
    this.indices.fold(CharArray(this.size)) { acc, rowIndex -> acc.apply { acc[rowIndex] = this@transpose[rowIndex][colIndex] } }
}.toTypedArray()

fun <T> T.println(): T = this.also { println(it) }
fun <T> List<T>.printLines(): List<T> = this.onEach { println(it) }


fun main(day: Day) {
    val testInput = eu.michalchomo.adventofcode.year2024.readInputLines("${day.name()}_test")
    println(day.part1(testInput))
    println(day.part2(testInput))

    val input = eu.michalchomo.adventofcode.year2024.readInputLines(day.name())
    println(day.part1(input))
    println(day.part2(input))
}
