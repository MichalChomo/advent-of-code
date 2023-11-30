sealed class Packet {
    data class PacketList(val l: List<Packet>) : Packet()
    data class PacketInt(val n: Int) : Packet()
}

fun main() {
    fun String.toPacket() = null

    fun part1(input: String): Int {
        input.split("\n\n")
            .asSequence()
            .map { it.split("\n").map { it.split(",").toList() } }
            .forEach { println(it) }
        return 0
    }

    fun part2(input: String): Int {
        return 0
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 42)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
