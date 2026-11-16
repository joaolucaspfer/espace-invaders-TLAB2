// Definição da nave espacial e funções de movimento e desenho.
package pt.isel.canvas

data class SpaceShip(val x: Int)

// Cria a nave na posição inicial centrada
fun createSpaceShip(): SpaceShip = SpaceShip((WIDTH - NAV_WIDTH) / 2)

// Move a nave centrando a posição no rato sem sair dos limites
fun SpaceShip.moveTo(mouseX: Int): SpaceShip {
    val newX = mouseX - NAV_WIDTH / 2
    val clampedX = if (newX < 0) 0 else if (newX > WIDTH - NAV_WIDTH) WIDTH - NAV_WIDTH else newX
    return copy(x = clampedX)
}

// Desenha a nave espacial com o corpo verde e o canhão amarelo
fun Canvas.drawShip(ship: SpaceShip) {
    drawRect(ship.x, NAV_Y, NAV_WIDTH, NAV_HEIGHT, GREEN)
    drawRect(ship.x + (NAV_WIDTH - AMARE_WIDTH) / 2, NAV_Y - AMARE_HEIGHT, AMARE_WIDTH, AMARE_HEIGHT, YELLOW)
}