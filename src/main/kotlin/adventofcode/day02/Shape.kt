package adventofcode.day02

private const val LOST = 0
private const val DRAW = 3
private const val WON = 6

data class Shape(
    private val name: String,
    private val beats: String,
    private val score: Int,
    val player2: Map<String, String>) {

    fun play(other: Shape): Int = when {
        name == other.name -> score + DRAW
        beats == other.name -> score + WON
        else -> score + LOST
    }

    override fun toString() = name
}
