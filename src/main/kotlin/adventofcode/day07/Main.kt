package adventofcode.day07

import java.io.*

fun main() {
    val dirs = File("requirements/day07/sample.txt").readText()
        .split(System.lineSeparator() + "$ cd")
        .map { it.split(System.lineSeparator()) }
        .map {
            it.map {
                if (it.startsWith(" ")) {
                    it.replace(" ", "$ cd ")
                } else it
            }
        }
    var i = 0
    val directories = dirs.map {
        if (it[0].endsWith("/")) {
            i = 0
        } else if (it[0].startsWith("$ cd ..")) {
            i--
        } else if (it[0].startsWith("$ cd")) {
            i++
        }
        Directory(it[0], it, if (i == 0) emptyList() else dirs[i - 1])
    }
    directories.filterNot { it.name.contains("..") }.forEach {
        println(it)
    }
}

fun main_sample() {
    val dirs = File("requirements/day07/sample.txt").readText()
        .split(System.lineSeparator() + "$ cd")
        .map { it.split(System.lineSeparator()) }

    val directories = dirs.map { dir ->
        Directory(dir[0].trim(), dir, emptyList())
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

data class Directory(val name: String, val dir: List<String>, val parentDir: List<String>) {

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
                "dirs=$dirs, " +
                "parentDirs=$parentDir" +
                ")"
    }

}
