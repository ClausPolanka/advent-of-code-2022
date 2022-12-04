package adventofcode.day02

private const val LOST = 0
private const val DRAW = 3
private const val WON = 6

private val beats = mutableMapOf(
    "Rock" to "Scissors",
    "Paper" to "Rock",
    "Scissors" to "Paper")

data class Shape(
    private val name: String,
    private val score: Int) {

    fun play(shape: Shape): Int = when {
        name == shape.name -> score + DRAW
        beats[name] == shape.name -> score + WON
        else -> score + LOST
    }

    override fun toString() = name
}
