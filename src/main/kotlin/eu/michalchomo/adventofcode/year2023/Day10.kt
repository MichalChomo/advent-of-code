package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.toCharMatrix

object Day10 : Day {

    override val number: Int = 10

    override fun part1(input: List<String>): String = input.toCharMatrix().findPath().let { it.size / 2 }.toString()

    override fun part2(input: List<String>): String = input.toCharMatrix().findPath().let {
        input.toCharMatrix().forEach { r -> println(r) }
        val pathColsInRow = it.fold(mutableMapOf<Int, List<Int>>()) { acc, (position, _) ->
            acc.merge(position.first, mutableListOf(position.second)) { old, _ ->
                (old + position.second).sorted()
            }
            acc
        }
        val pathRowsInCol = it.fold(mutableMapOf<Int, List<Int>>()) { acc, (position, _) ->
            acc.merge(position.second, mutableListOf(position.first)) { old, _ ->
                (old + position.first).sorted()
            }
            acc
        }

    }.toString()

    private fun Pair<Int, Int>.directions(pipe: Char, direction: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val newDirection = when (direction) {
            0 to -1 -> when (pipe) { // left
                '-' -> 0 to -1
                'L' -> -1 to 0
                'F' -> 1 to 0
                else -> throw IllegalStateException("Wrong path")
            }
            0 to 1 -> when (pipe) { // right
                '-' -> 0 to 1
                'J' -> -1 to 0
                '7' -> 1 to 0
                else -> throw IllegalStateException("Wrong path")
            }
            -1 to 0 -> when (pipe) { // up
                '|' -> -1 to 0
                '7' -> 0 to -1
                'F' -> 0 to 1
                else -> throw IllegalStateException("Wrong path")
            }
            1 to 0 -> when (pipe) { // down
                '|' -> 1 to 0
                'L' -> 0 to 1
                'J' -> 0 to -1
                else -> throw IllegalStateException("Wrong path")
            }
            else -> throw IllegalStateException("Wrong path")
        }

        return (this.first + newDirection.first) to (this.second + newDirection.second) to newDirection
    }

    private fun Array<CharArray>.findPath(): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val startRow = this.indexOfFirst { 'S' in it }
        val startCol = this[startRow].indexOf('S')
        // TODO the startCol + 1 is a manual hack, code it
        val path = mutableListOf((startRow to startCol+1) to (0 to 1))
        while (path.size == 1 || path.last().first != (startRow to startCol)) {
            val position = path.last().first
            val directions = path.last().second
            path.add(position.directions(this[position.first][position.second], directions))
        }
        return path
    }

}

fun main() {
    main(Day10)
}
