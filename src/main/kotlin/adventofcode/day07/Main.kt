package adventofcode.day07

import java.io.*

fun main() {
    val inputLines =
        File("requirements/day07/input.txt").useLines { it.toList() }

    val dirToSize = buildMap {
        put("", 0)
        var cwd = ""
        for (line in inputLines) {
            val match = """[$] cd (.*)|(\d+).*""".toRegex().matchEntire(line)
                ?: continue
            val directory: String? = match.groups[1]?.value
            val fileSize: Int? = match.groups[2]?.value?.toIntOrNull()
            directory?.let { d ->
                cwd = createPathForDirectory(d, cwd)
            } ?: fileSize?.let { fs ->
                var dir = cwd
                while (true) {
                    put(dir, getOrElse(dir) { 0 } + fs)
                    if (dir.isEmpty()) {
                        break
                    }
                    dir = dir.substringBeforeLast('/', "")
                }
            }
        }
    }

    println("Part 1: Sum of directories with size <= 100.000: "
            + dirToSize.values.filter { it <= 100_000 }.sum())
}

fun createPathForDirectory(d: String, cwd: String) = when (d) {
    "/" -> ""
    ".." -> cwd.substringBeforeLast('/', "")
    else -> if (cwd.isEmpty()) d else "$cwd/$d"
}
