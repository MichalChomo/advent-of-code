import kotlin.math.abs
import kotlin.math.max

enum class Motion {
    R, U, L, D
}

fun main() {

    fun List<List<Int>>.printMotion() = println(
        (10 downTo -10).joinToString(separator = "\n") { i ->
            (-10..10).joinToString(separator = " ") { j ->
                when {
                    i !in -9..9 -> "% 3d".format(j)
                    j == 10 -> "% 3d".format(i)
                    this.contains(listOf(i, j)) -> "  ${this.indexOf(listOf(i, j))}"
                    i == 0 && j == 0 -> "  s"
                    else -> "  ."
                }
            }
        } + "\n\n"
    )

    fun List<Int>.distanceRow(other: List<Int>) = abs(this[0] - other[0])
    fun List<Int>.distanceCol(other: List<Int>) = abs(this[1] - other[1])

    fun List<Int>.directionRow(other: List<Int>) = if (this[0] == other[0]) 0 else if (this[0] - other[0] > 0) -1 else 1
    fun List<Int>.directionCol(other: List<Int>) = if (this[1] == other[1]) 0 else if (this[1] - other[1] > 0) -1 else 1

    fun List<Int>.distance(other: List<Int>) =
        max(distanceRow(other), distanceCol(other))

    fun MutableList<Int>.follow(other: List<Int>) {
        if (distance(other) > 1) {
            if (distanceRow(other) > 0 && distanceCol(other) > 0) {
                this[0] += directionRow(other)
                this[1] += directionCol(other)
            } else if (distanceRow(other) > 0) {
                this[0] = if (other[0] > this[0]) this[0] + 1 else this[0] - 1
            } else {
                this[1] = if (other[1] > this[1]) this[1] + 1 else this[1] - 1
            }
        }
    }

    fun part1(input: List<String>): Int = input.map { it.split(" ") }
        .map { (motion, step) -> Motion.valueOf(motion) to step.toInt() }
        .fold(
            Triple(
                mutableListOf(0, 0),
                mutableListOf(0, 0),
                mutableSetOf(listOf(0, 0))
            )
        ) { (head, tail, tailVisited), (motion, step) ->
            repeat(step) {
                when (motion) {
                    Motion.R -> head[1] += 1
                    Motion.U -> head[0] += 1
                    Motion.L -> head[1] -= 1
                    Motion.D -> head[0] -= 1
                }
                tail.follow(head)
                tailVisited.add(tail.toList())
            }
            Triple(head, tail, tailVisited)
        }
        .third.size

    fun part2(input: List<String>): Int = input.map { it.split(" ") }
        .map { (motion, step) -> Motion.valueOf(motion) to step.toInt() }
        .fold(
            List(10) { mutableListOf(0, 0) }.let { knots ->
                Triple(
                    knots[0],
                    knots,
                    mutableSetOf(listOf(0, 0))
                )
            }
        ) { (head, knots, tailVisited), (motion, step) ->
            repeat(step) {
                when (motion) {
                    Motion.R -> head[1] += 1
                    Motion.U -> head[0] += 1
                    Motion.L -> head[1] -= 1
                    Motion.D -> head[0] -= 1
                }
                (1 until 10).forEach {
                    knots[it].follow(knots[it - 1])
                    if (it == 9) tailVisited.add(knots[it].toList())
                }
            }
            Triple(knots[0], knots, tailVisited)
        }
        .third.size

    val testInput1 = readInputLines("Day09_test")
    val testInput2 = readInputLines("Day09_test2")
    check(part1(testInput1) == 13)
    check(part2(testInput2) == 36)

    val input = readInputLines("Day09")
    println(part1(input))
    println(part2(input))
}
