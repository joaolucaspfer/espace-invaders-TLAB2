/** Main game logic and event handling */
package pt.isel.canvas

import kotlin.random.Random

fun runGame() {
    var playerX = (WIDTH - NAV_WIDTH) / 2
    var aliens = listOf<Alien>()
    var playerShot: Shot? = null
    var gameOver = false

    onStart {
        val cv = Canvas(WIDTH, HEIGHT, BLACK)

        fun shoot() {
            if (playerShot == null && !gameOver) {
                val shotX = playerX + (NAV_WIDTH - ALIEN_WIDTH) / 2
                val shotY = NAV_Y - AMARE_HEIGHT - ALIEN_HEIGHT
                playerShot = Shot(shotX, shotY)
            }
        }

        cv.drawShip(playerX)

        cv.onMouseMove { mouseEvent ->
            if (!gameOver) {
                playerX = mouseEvent.x - NAV_WIDTH / 2
                if (playerX < 0) playerX = 0
                if (playerX > WIDTH - NAV_WIDTH) playerX = WIDTH - NAV_WIDTH
            }
        }

        cv.onMouseDown {
            shoot()
        }

        cv.onKeyPressed { keyEvent ->
            if (keyEvent.char == ' ' || keyEvent.code == 32) {
                shoot()
            }
        }

        cv.onTimeProgress(250) {
            if (!gameOver && Random.nextInt(100) < 50) {
                val x = Random.nextInt(0, WIDTH - ALIEN_WIDTH)
                val speed = Random.nextInt(1, 5)
                aliens = aliens + Alien(x, 0, speed)
            }
        }

        cv.onTimeProgress(1000 / FPS) {
            if (gameOver) return@onTimeProgress

            playerShot?.let { currentShot ->
                val newY = currentShot.y - SHOT_SPEED
                playerShot = if (newY + ALIEN_HEIGHT > 0) currentShot.copy(y = newY) else null
            }

            aliens = aliens
                .map { it.copy(y = it.y + it.speed) }
                .filter { it.y < HEIGHT }

            playerShot?.let { currentShot ->
                val hitAlien = aliens.find { alien ->
                    currentShot.x < alien.x + ALIEN_WIDTH &&
                            currentShot.x + ALIEN_WIDTH > alien.x &&
                            currentShot.y < alien.y + ALIEN_HEIGHT &&
                            currentShot.y + ALIEN_HEIGHT > alien.y
                }
                if (hitAlien != null) {
                    aliens = aliens - hitAlien
                    playerShot = null
                }
            }

            val hitShip = aliens.any { alien ->
                alien.x < playerX + NAV_WIDTH &&
                        alien.x + ALIEN_WIDTH > playerX &&
                        alien.y + ALIEN_HEIGHT >= NAV_Y &&
                        alien.y <= NAV_Y + NAV_HEIGHT
            }

            if (hitShip) {
                gameOver = true
            }

            cv.erase()
            cv.drawShip(playerX)
            playerShot?.let { cv.drawShot(it) }
            for (alien in aliens) {
                cv.drawAlien(alien)
            }

            if (gameOver) {
                cv.drawText(WIDTH / 2 - 80, HEIGHT / 2, "GAME OVER", RED, 32)
            }
        }
    }
}