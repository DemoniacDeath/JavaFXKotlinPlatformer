package main

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color

class RenderObject(
        val texture: Image
) {
    var flipped: Boolean = false

    fun render(
            graphics: GraphicsContext,
            context: GameContext,
            position: Vector,
            size: Size,
            cameraPosition: Vector,
            cameraSize: Size
    ) {
        val renderPosition = position +
                Vector(-size.width / 2, -size.height / 2) -
                cameraPosition -
                Vector(-cameraSize.width / 2, -cameraSize.height / 2)
        graphics.drawImage(
                texture,
                0.0, 0.0, texture.width, texture.height,
                Math.round(context.windowSize.width * (renderPosition.x / cameraSize.width)).toDouble(),
                Math.round(context.windowSize.height * (renderPosition.y / cameraSize.height)).toDouble(),
                Math.round(context.windowSize.width * (size.width / cameraSize.width)).toDouble(),
                Math.round(context.windowSize.height * (size.height / cameraSize.height)).toDouble()
        )
    }
    companion object {
        fun fromColor(color: Color): RenderObject
        {
            val wi = WritableImage(1, 1)
            wi.pixelWriter.setColor(0, 0, color)
            return RenderObject(wi)

        }
    }
}

