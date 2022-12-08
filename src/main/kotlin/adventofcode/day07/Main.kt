package adventofcode.day07

import java.io.*

fun main() {
    val dirs = File("requirements/day07/sample.txt").readText()
        .split(System.lineSeparator() + "$ cd")
        .map { it.split(System.lineSeparator()) }

    val directories = dirs.map { dir ->
        Directory(dir[0].trim(), dir)
    }

    directories.forEach { d ->
        d.dirsNames.forEach { subDir ->
            d.dirs.addAll(directories.filter { it.name == subDir })
        }
    }

    println(
        directories.filter { d ->
            d.filesSizeSum + d.dirs.sumOf { it.filesSizeSum } <= 100000
        }.sumOf { d -> d.filesSizeSum + d.dirs.sumOf { it.filesSizeSum } }
    )

    directories.forEach {
        println(it)
    }
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
                "name='$name', " +
                "files=$files, " +
                "filesSizeSum=$filesSizeSum, " +
                "dirsFileSizeSum=$dirsFileSizeSum, " +
                "dirsNames=$dirsNames, " +
                "dirs=$dirs" +
                ")"
    }

}
