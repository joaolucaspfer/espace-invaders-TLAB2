/** Shot data type and drawing function */
package pt.isel.canvas

data class Shot(val x: Int, val y: Int)

fun Canvas.drawShot(shot: Shot) {
    drawRect(shot.x, shot.y, ALIEN_WIDTH, ALIEN_HEIGHT, WHITE)
}