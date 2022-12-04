package adventofcode.day02

private const val LOST = 0
private const val DRAW = 3
private const val WON = 6

class Game(private val player1: Shape, private val player2: Shape) {
    fun player2Score(): Int = when (player1.name) {
        player2.name -> player2.score + DRAW
        player2.beats -> player2.score + WON
        else -> player2.score + LOST
    }
}
