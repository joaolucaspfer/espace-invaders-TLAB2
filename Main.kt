import pt.isel.canvas.*

const val WIDTH = 700
const val HEIGHT = 500

const val NAV_WIDTH = 50
const val NAV_HEIGHT = 10
const val TURRET_WIDTH = 4
const val TURRET_HEIGHT = 5
const val NAV_Y = 450

// Desenha a nave espacial (retângulo verde + canhão amarelo)
fun Canvas.drawShip(x: Int) {
    drawRect(x, NAV_Y, NAV_WIDTH, NAV_HEIGHT, GREEN)
    drawRect(x + (NAV_WIDTH - TURRET_WIDTH) / 2, NAV_Y - TURRET_HEIGHT, TURRET_WIDTH, TURRET_HEIGHT, YELLOW)
}

fun main() {
    var playerX = (WIDTH - NAV_WIDTH) / 2

    onStart {
        val cv = Canvas(WIDTH, HEIGHT, BLACK)

        // Desenha a nave inicial
        cv.drawShip(playerX)

        cv.onMouseMove { mouseEvent ->
            // Centra o movimento do rato no meio da nave
            playerX = mouseEvent.x - NAV_WIDTH / 2

            // Garante que a nave não sai da área de jogo
            if (playerX < 0) playerX = 0
            if (playerX > WIDTH - NAV_WIDTH) playerX = WIDTH - NAV_WIDTH

            cv.erase()
            // Desenha a nave na nova posição
            cv.drawShip(playerX)
        }
    }
}
