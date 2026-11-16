// Definição do projétil da nave espacial e sua função de desenho.
package pt.isel.canvas

data class Shot(val x: Int, val y: Int)

// Desenha o projétil da nave espacial como um retângulo branco
fun Canvas.drawShot(shot: Shot) {
    drawRect(shot.x, shot.y, SHOT_WIDTH, SHOT_HEIGHT, WHITE)
}