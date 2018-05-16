package main

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class RenderObject(
        val texture: Image
) {
    fun render(
            graphics: GraphicsContext,
            context: GameContext,
            vector: Vector,
            size: Size,
            cameraPosition: Vector,
            cameraSize: Size
    ) {
    }
}