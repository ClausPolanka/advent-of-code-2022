package adventofcode.day12.djleeds

import java.io.*
import java.math.*
import java.security.*

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> =
    File("requirements", "$name.txt").readLines()

fun readInputAsText(name: String): String =
    File("requirements", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

var debug = false

fun log(message: () -> Any) {
    if (debug) println(message())
}

fun log(message: Any) {
    if (debug) println(message)
}
