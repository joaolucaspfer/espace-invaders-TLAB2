// Definição dos projéteis alienígenas e sua função de desenho.
package pt.isel.canvas

data class Alien(val x: Int, val y: Int, val speed: Int)

// Desenha o projétil alienígena como um retângulo vermelho
fun Canvas.drawAlien(alien: Alien) {
    drawRect(alien.x, alien.y, SHOT_WIDTH, SHOT_HEIGHT, RED)
}