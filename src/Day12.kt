import java.util.PriorityQueue
import kotlin.math.abs

data class Point(
    val row: Int,
    val col: Int,
)

data class Graph(val edges: Map<Point, List<Point>>) {

    fun shortestPath(start: Point, destination: Point): Int {
        val costSoFar = mutableMapOf(start to 0)
        val cameFrom = mutableMapOf<Point, Point>()
        val open = PriorityQueue<Pair<Point, Int>> { o1, o2 ->
            costSoFar[o1.first]?.compareTo(costSoFar[o2.first] ?: 0) ?: 0
        }
            .apply { add(start to 0) }

        while (open.isNotEmpty()) {
            open.remove().let { (current, _) ->
                if (current == destination) return costSoFar[current] ?: 0

                edges[current]?.forEach { next ->
                    val newCost = costSoFar[current]!! + 1
                    if (next !in costSoFar || newCost < costSoFar[next]!!) {
                        costSoFar[next] = newCost
                        open.add(next to newCost + heuristic(next, destination))
                        cameFrom[next] = current
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    private fun heuristic(point: Point, destination: Point): Int =
        abs(destination.row - point.row) + abs(destination.col - point.col)

}

fun main() {

    fun Char.isNotFurtherThanOneFrom(other: Char) = until(other).count() <= 1

    fun Char.canStepTo(other: Char) = when (this) {
        'S' -> 'a'.isNotFurtherThanOneFrom(other)
        'E' -> true
        else -> when (other) {
            'S' -> isNotFurtherThanOneFrom('a')
            'E' -> isNotFurtherThanOneFrom('z')
            else -> isNotFurtherThanOneFrom(other)
        }
    }

    fun List<String>.findNeighbors(row: Int, col: Int): List<Point> = (row - 1..row + 1)
        .filter { rowIndex -> rowIndex in (0 until this.size) }
        .map { rowIndex ->
            (col - 1..col + 1)
                .filter { colIndex ->
                    colIndex in (0 until this.first().length)
                            && ((colIndex != col && rowIndex == row) || (colIndex == col && rowIndex != row))
                            && this[row][col].canStepTo(this[rowIndex][colIndex])
                }
                .map { colIndex -> Point(rowIndex, colIndex) }
        }
        .flatten()

    fun List<String>.toGraph() = mapIndexed { rowIndex, cols ->
        cols.mapIndexed { colIndex, col -> Point(rowIndex, colIndex) }
    }
        .flatten()
        .fold(mutableMapOf<Point, List<Point>>()) { acc, point ->
            acc.apply { putAll(this@toGraph.findNeighbors(point.row, point.col).groupBy { point }) }
        }.let { Graph(it) }

    fun List<String>.posOfUniqueChar(c: Char) = asSequence()
        .indexOfFirst { it.contains(c) }
        .let { it to this[it].indexOf(c) }

    fun List<String>.startingPoints(): List<Point> = asSequence()
        .flatMapIndexed { index, s -> s.indices.filter { s[it] == 'a' }.map { index to it } }
        .map { Point(it.first, it.second) }
        .toList()

    fun List<String>.startingPos(): Pair<Int, Int> = posOfUniqueChar('S')
    fun List<String>.destinationPos(): Pair<Int, Int> = posOfUniqueChar('E')

    fun List<String>.startingPoint(): Point = startingPos().let { Point(it.first, it.second) }
    fun List<String>.destinationPoint(): Point = destinationPos().let { Point(it.first, it.second) }

    fun part1(input: List<String>): Int = input.toGraph()
        .run {
            shortestPath(input.startingPoint(), input.destinationPoint())
        }

    fun part2(input: List<String>): Int = input.toGraph()
        .run {
            input.startingPoints().plus(input.startingPoint())
                .minOfOrNull { shortestPath(it, input.destinationPoint()) } ?: 0
        }

    val testInput = readInputLines("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInputLines("Day12")
    println(part1(input))
    println(part2(input))
}
