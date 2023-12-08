package eu.michalchomo.adventofcode.year2023

import eu.michalchomo.adventofcode.Day
import eu.michalchomo.adventofcode.main

object Day08 : Day {

    override val number: Int = 8

    override fun part1(input: List<String>): String = input.parseToMap().let { map: Map<String, Pair<String, String>> ->
        val instructions = input[0]
        solve(instructions, map, "AAA") { it == "ZZZ" }
    }.toString()

    override fun part2(input: List<String>): String = input.drop(2).mapNotNull { line ->
        if ("A " in line) {
            line.split(" ")[0]
        } else null
    }.let { endingWithA ->
        val instructions = input[0]
        val map = input.parseToMap()
        endingWithA.map { solve(instructions, map, it) { it.last() == 'Z' } }.toList().lcm()
    }.toString()

    private fun solve(
        instructions: String,
        map: Map<String, Pair<String, String>>,
        firstElement: String,
        predicate: (String) -> Boolean
    ): Int {
        var result = 0
        var mutableFirstElement = firstElement
        while (!predicate(mutableFirstElement)) {
            val (steps, newLastElement) = instructions.fold(0 to mutableFirstElement) { (steps, node), instruction ->
                val (left, right) = map[node]!!
                val element = if (instruction == 'L') left else right
                (steps + 1) to element
            }
            mutableFirstElement = newLastElement
            result += steps
        }
        return result
    }

    private fun List<String>.parseToMap(): Map<String, Pair<String, String>> = this.drop(2).associate {
        val nodeLeftRight = it.replace(" ", "").replace("(", "").replace(")", "")
            .split('=', ',')
        nodeLeftRight[0] to (nodeLeftRight[1] to nodeLeftRight[2])
    }

    private fun gcd(a: Long, b: Long): Long {
        var a = a
        var b = b
        while (b != 0L) {
            val temp = b
            b = a % b
            a = temp
        }
        return a
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    private fun List<Int>.lcm(): Long {
        var result = this[0].toLong()
        for (i in 1 until this.size) {
            result = lcm(result, this[i].toLong())
        }
        return result
    }

}

fun main() {
    main(Day08)
}
