import java.io.File

fun readInput(name: String) = File("src", "$name.txt").readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(name: String) = File("src", "$name.txt").readLines()

fun linesAsInts(lines: List<String>) = lines.map { l -> l.toInt() }

fun Boolean.toInt() = if (this) 1 else 0