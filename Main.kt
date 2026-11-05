import pt.isel.canvas.*

const val WIDTH = 700
const val HEIGHT = 500

const val PLAYER_SIZE = 50
const val PLAYER_Y = HEIGHT - 2 * PLAYER_SIZE

fun main() {
    var playerX = WIDTH / 2 - PLAYER_SIZE / 2

    onStart {
        val cv = Canvas(WIDTH, HEIGHT, BLACK)

        cv.drawRect(playerX, PLAYER_Y, PLAYER_SIZE * 2, PLAYER_SIZE / 4, GREEN)

        cv.onMouseMove { mouseEvent ->
            playerX = mouseEvent.x - PLAYER_SIZE / 2

            if (playerX < 0) playerX = 0
            if (playerX > WIDTH - PLAYER_SIZE * 2) playerX = WIDTH - PLAYER_SIZE * 2

            cv.erase()
            cv.drawRect(playerX, PLAYER_Y, PLAYER_SIZE * 2, PLAYER_SIZE / 4, GREEN)
        }
    }
}