/** Spaceship drawing and position handling */
package pt.isel.canvas

fun Canvas.drawShip(x: Int) {
    drawRect(x, NAV_Y, NAV_WIDTH, NAV_HEIGHT, GREEN)
    drawRect(x + (NAV_WIDTH - AMARE_WIDTH) / 2, NAV_Y - AMARE_HEIGHT, AMARE_WIDTH, AMARE_HEIGHT, YELLOW)
}