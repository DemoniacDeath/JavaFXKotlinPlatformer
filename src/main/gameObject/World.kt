package main.gameObject

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import main.GameContext
import main.GameObject
import main.Rect

class World(context: GameContext, frame: Rect) : GameObject(context, frame) {
    val camera = Camera(context, frame)

    fun render(graphics: GraphicsContext) {
        super.render(graphics, frame.center, camera.globalPosition(), camera.frame.size)
    }

    override fun keyDown(key: KeyCode) {
        super.keyDown(key)
        if (key == KeyCode.Q) context.quit = true
    }
}