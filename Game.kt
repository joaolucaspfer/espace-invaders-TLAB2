// Representação do estado do jogo e funções de atualização e renderização.
package pt.isel.canvas

import kotlin.random.Random

data class Game(
    val ship: SpaceShip,
    val aliens: List<Alien>,
    val playerShot: Shot?,
    val isOver: Boolean
)

// Cria o estado inicial do jogo
fun createGame(): Game = Game(
    ship = createSpaceShip(),
    aliens = emptyList(),
    playerShot = null,
    isOver = false
)

// Atualiza a posição da nave no estado do jogo
fun Game.moveShip(mouseX: Int): Game =
    if (isOver) this else copy(ship = ship.moveTo(mouseX))

// Cria um disparo da nave se ainda não existir nenhum disparo ativo
fun Game.shoot(): Game {
    if (isOver || playerShot != null) return this
    val shotX = ship.x + (NAV_WIDTH - SHOT_WIDTH) / 2
    val shotY = NAV_Y - AMARE_HEIGHT - SHOT_HEIGHT
    return copy(playerShot = Shot(shotX, shotY))
}

// Tenta criar um novo projétil alienígena com base na probabilidade
fun Game.trySpawnAlien(): Game {
    if (isOver || Random.nextInt(100) >= ALIEN_SPAWN_PROBABILITY) return this
    val x = Random.nextInt(0, WIDTH - SHOT_WIDTH)
    val speed = Random.nextInt(1, 5) // velocidade entre 1 e 4 pixels
    return copy(aliens = aliens + Alien(x, 0, speed))
}

// Avança um frame do jogo: move projéteis, deteta colisões e atualiza o estado
fun Game.step(): Game {
    if (isOver) return this

    // 1. Mover projétil da nave para cima
    val movedPlayerShot = playerShot?.let { currentShot ->
        val nextY = currentShot.y - SHOT_SPEED
        if (nextY + SHOT_HEIGHT > 0) currentShot.copy(y = nextY) else null
    }

    // 2. Mover projéteis dos aliens para baixo
    val movedAliens = aliens
        .map { it.copy(y = it.y + it.speed) }
        .filter { it.y < HEIGHT }

    // 3. Deteção de colisão: projétil da nave contra projéteis alienígenas
    val hitAlien = if (movedPlayerShot != null) {
        movedAliens.find { alien ->
            movedPlayerShot.x < alien.x + SHOT_WIDTH &&
            movedPlayerShot.x + SHOT_WIDTH > alien.x &&
            movedPlayerShot.y < alien.y + SHOT_HEIGHT &&
            movedPlayerShot.y + SHOT_HEIGHT > alien.y
        }
    } else null

    val finalPlayerShot = if (hitAlien != null) null else movedPlayerShot
    val remainingAliens = if (hitAlien != null) movedAliens - hitAlien else movedAliens

    // 4. Deteção de colisão: projétil alienígena contra o retângulo verde da nave
    val shipHit = remainingAliens.any { alien ->
        alien.x < ship.x + NAV_WIDTH &&
        alien.x + SHOT_WIDTH > ship.x &&
        alien.y + SHOT_HEIGHT >= NAV_Y &&
        alien.y <= NAV_Y + NAV_HEIGHT
    }

    return copy(
        playerShot = finalPlayerShot,
        aliens = remainingAliens,
        isOver = shipHit
    )
}

// Desenha todo o estado do jogo no canvas
fun Canvas.drawGame(game: Game) {
    erase()
    drawShip(game.ship)
    game.playerShot?.let { drawShot(it) }
    for (alien in game.aliens) {
        drawAlien(alien)
    }
    if (game.isOver) {
        drawText(WIDTH / 2 - 80, HEIGHT / 2, "GAME OVER", RED, 32)
    }
}

// Função principal de execução com o único ponto de mutabilidade
fun runGame() {
    var game = createGame() // Único ponto de mutabilidade do programa

    onStart {
        val cv = Canvas(WIDTH, HEIGHT, BLACK)
        cv.drawGame(game)

        cv.onMouseMove { mouseEvent ->
            game = game.moveShip(mouseEvent.x)
        }

        cv.onMouseDown {
            game = game.shoot()
        }

        cv.onKeyPressed { keyEvent ->
            if (keyEvent.char == ' ' || keyEvent.code == 32) {
                game = game.shoot()
            }
        }

        cv.onTimeProgress(ALIEN_SPAWN_INTERVAL) {
            game = game.trySpawnAlien()
        }

        cv.onTimeProgress(1000 / FPS) {
            game = game.step()
            cv.drawGame(game)
        }
    }
}