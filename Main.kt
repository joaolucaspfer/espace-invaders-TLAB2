import pt.isel.canvas.*
import kotlin.random.Random

const val WIDTH = 700
const val HEIGHT = 500

const val NAV_WIDTH = 50
const val NAV_HEIGHT = 10
const val AMARE_WIDTH = 4
const val AMARE_HEIGHT = 5
const val NAV_Y = 504

const val ALIEN_WIDTH = 4
const val ALIEN_HEIGHT = 7
const val FPS = 70

data class Alien(val x: Int, val y: Int, val speed: Int)

fun Canvas.drawShip(x: Int) {
    drawRect(x, NAV_Y, NAV_WIDTH, NAV_HEIGHT, GREEN)
    drawRect(x + (NAV_WIDTH - AMARE_WIDTH) / 2, NAV_Y - AMARE_HEIGHT, AMARE_WIDTH, AMARE_HEIGHT, YELLOW)
}

fun Canvas.drawAlien(alien: Alien) {
    drawRect(alien.x, alien.y, ALIEN_WIDTH, ALIEN_HEIGHT, RED)
}

fun main() {
    var playerX = (WIDTH - NAV_WIDTH) / 2
    var aliens = listOf<Alien>()

    onStart {
        val cv = Canvas(WIDTH, HEIGHT, BLACK)

        cv.drawShip(playerX)

        cv.onMouseMove { mouseEvent ->
            playerX = mouseEvent.x - NAV_WIDTH / 2
            if (playerX < 0) playerX = 0
            if (playerX > WIDTH - NAV_WIDTH) playerX = WIDTH - NAV_WIDTH
        }

        cv.onTimeProgress(250) {
            if (Random.nextInt(100) < 50) {
                val x = Random.nextInt(0, WIDTH - ALIEN_WIDTH)
                val speed = Random.nextInt(1, 5)
                aliens = aliens + Alien(x, 0, speed)
            }
        }

        cv.onTimeProgress(1000 / FPS) {
            aliens = aliens
                .map { it.copy(y = it.y + it.speed) }
                .filter { it.y < HEIGHT }

            cv.erase()
            cv.drawShip(playerX)
            for (alien in aliens) {
                cv.drawAlien(alien)
            }
        }
    }
}
