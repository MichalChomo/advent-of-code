package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main
import eu.michalchomo.adventofcode.toCharMatrix

object Day10 : Day {

    override val number: Int = 10

    private const val IN_CHAR = 'I'
    private const val OUT_CHAR = 'O'

    override fun part1(input: List<String>): String = input.toCharMatrix().findPath().let { it.size / 2 }.toString()

    override fun part2(input: List<String>): String = input.toCharMatrix().let { charMatrix ->
        charMatrix.findPath().let { path ->
            val pathColsInRow = path.fold(mutableMapOf<Int, List<Int>>()) { acc, (position, _) ->
                acc.merge(position.first, mutableListOf(position.second)) { old, _ ->
                    (old + position.second).sorted()
                }
                acc
            }
            val matCopy = charMatrix.copyOf()
            charMatrix.indices.sumOf { i ->
                val pathCols = pathColsInRow[i] ?: emptyList()
                charMatrix[i].indices.count { j ->
                    if (j !in pathCols) {
                        val crossings = pathCols.findPathTilesBefore(j)
                            .minus(charMatrix[i].slice(0..<j).count { it == '-' || it == 'F' || it == '7' })
                        val isInside = crossings.isOdd()
                        if (isInside) {
                            matCopy[i][j] = IN_CHAR
                        } else {
                            matCopy[i][j] = OUT_CHAR
                        }
                        isInside
                    } else {
                        false
                    }
                }
            }
                .also {
                    matCopy.forEach { r ->
                        r.forEach { c ->
                            if (c == IN_CHAR) {
                                print("\u001b[31m$c\u001b[0m")
                            } else if (c == OUT_CHAR) {
                                print("\u001b[34m$c\u001b[0m")
                            } else {
                                print(c)
                            }
                        }
                        println()
                    }
                }
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
        val path = mutableListOf(this.findStartPositionAndDirection(startRow to startCol))
        while (path.size == 1 || path.last().first != (startRow to startCol)) {
            val position = path.last().first
            val directions = path.last().second
            path.add(position.directions(this[position.first][position.second], directions))
        }
        return path
    }

    private fun Array<CharArray>.findStartPositionAndDirection(start: Pair<Int, Int>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        // Search all straight directions from the start
        listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).forEach { direction ->
            val position = (start.first + direction.first).coerceAtLeast(0) to (start.second + direction.second).coerceAtMost(this.size-1)
            try {
                // If this does not fail, then the path is correct
                position.directions(this[position.first][position.second], direction)
                // Use the current position, so it's only one step from the start
                return position to direction
            } catch (_: Throwable) {}
        }
        throw IllegalStateException("No start position found")
    }

    private fun List<Int>?.findPathTilesBefore(index: Int) = this?.indexOfLast { it < index }?.plus(1) ?: 0

    private fun Int.isOdd() = this % 2 == 1

}

fun main() {
    main(Day10)
}
