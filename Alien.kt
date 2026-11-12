/** Alien data type and drawing function */
package pt.isel.canvas

data class Alien(val x: Int, val y: Int, val speed: Int)

fun Canvas.drawAlien(alien: Alien) {
    drawRect(alien.x, alien.y, ALIEN_WIDTH, ALIEN_HEIGHT, RED)
}