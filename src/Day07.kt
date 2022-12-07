sealed class File
data class DataFile(val size: Long) : File()
data class Directory(val path: String, val files: MutableList<File>) : File() {
    fun size(): Long = files.sumOf {
        when (it) {
            is DataFile -> it.size
            is Directory -> it.size()
        }
    }
}

data class MutablePair(var currentPath: String, val pathToDirectory: MutableMap<String, Directory>)

fun main() {
    fun String.removeLastPathElement() = substringBeforeLast("/").ifEmpty { "/" }

    fun List<String>.toDirectories(): List<Directory> =
        fold(MutablePair("", mutableMapOf("/" to Directory("/", mutableListOf())))) { acc, s ->
            when {
                s == "$ ls" -> acc
                s == "$ cd .." -> acc.apply { currentPath = currentPath.removeLastPathElement() }
                s.startsWith("$ cd") -> acc.apply {
                    val path = if (currentPath.length < 2) currentPath else "$currentPath/"
                    currentPath = path + s.split(" ")[2]
                }

                s.startsWith("dir") -> acc.apply {
                    val path = if (currentPath == "/") currentPath else "$currentPath/"
                    Directory(path + s.split(" ")[1], mutableListOf()).let {
                        pathToDirectory[it.path] = it
                        pathToDirectory[currentPath]?.files?.add(it)
                    }
                }

                else -> acc.apply {
                    pathToDirectory[currentPath]?.files?.add(DataFile(s.split(" ")[0].toLong()))
                }
            }
        }
            .pathToDirectory.values
            .toList()

    fun part1(input: List<String>): Long = input.toDirectories()
        .asSequence()
        .map { it.size() }
        .filter { it < 100_000 }
        .sum()

    fun part2(input: List<String>): Long = input.toDirectories().let { directories ->
        val neededSpace = directories.asSequence()
            .filter { it.path == "/" }
            .map { it.size() }
            .first()
            .let { usedSpace -> 30_000_000 - (70_000_000 - usedSpace) }

        directories.asSequence()
            .map { it.size() }
            .filter { it >= neededSpace }
            .min()
    }

    val testInput = readInputLines("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInputLines("Day07")
    println(part1(input))
    println(part2(input))
}
