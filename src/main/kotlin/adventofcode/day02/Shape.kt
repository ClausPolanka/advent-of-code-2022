package adventofcode.day02

data class Shape(
    val name: String,
    val beats: String,
    val score: Int,
    val player2: Map<String, String>) {

    override fun toString() = name
}
