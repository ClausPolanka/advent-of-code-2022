package adventofcode.day07

import java.io.*

fun main() {
    val dirs = File("requirements/day07/sample.txt").readText()
        .split(System.lineSeparator() + "$ cd")
        .map { it.split(System.lineSeparator()) }

    val dirsList = dirs.map { dir ->
        Directory(dir[0].trim(), dir)
    }

    dirsList.forEach { d ->
        d.dirsNames.forEach { subDir ->
            d.dirs.addAll(dirsList.filter { it.name == subDir })
        }
    }

    println(
        dirsList.filter { d ->
            d.filesSizeSum + d.dirs.sumOf { it.filesSizeSum } <= 100000
        }.sumOf { d -> d.filesSizeSum + d.dirs.sumOf { it.filesSizeSum } }
    )
}

data class Directory(val name: String, val dir: List<String>) {

    val dirsNames = dir
        .filter { it.startsWith("dir") }
        .map { it.removePrefix("dir ") }

    val files = dir
        .drop(1)
        .filter { it.isNotBlank() }
        .filterNot { it.startsWith("dir") }
        .filterNot { it.startsWith("$") }

    val filesSizeSum = files.map { it.split(" ")[0] }.sumOf { it.toInt() }

    val dirs = mutableListOf<Directory>()

    val dirsFileSizeSum = dirs.map { it.filesSizeSum }.sum()

    override fun toString(): String {
        return "Directory(" +
                "name='$name', ${System.lineSeparator()} " +
                "dirsNames=$dirsNames, ${System.lineSeparator()} " +
                "files=$files, ${System.lineSeparator()} " +
                "filesSizeSum=$filesSizeSum, ${System.lineSeparator()}" +
                "dirsFileSizeSum=$dirsFileSizeSum, ${System.lineSeparator()}" +
                "dirs=${dirs}${System.lineSeparator()}" +
                ")"
    }

}
