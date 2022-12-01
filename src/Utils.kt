import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput(name: String) = File("src", "$name.txt").readText()

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(name: String) = File("src", "$name.txt").readLines()

fun linesAsInts(lines: List<String>) = lines.map { l -> l.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
