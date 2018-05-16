package main.gameObject.ui

import javafx.scene.canvas.GraphicsContext
import main.GameContext
import main.Rect
import main.Vector

class UI(context: GameContext, frame: Rect) : Element(context, frame) {
    var powerBar: Element? = null

    fun render(graphics: GraphicsContext) {
        super.render(
                graphics,
                frame.center,
                Vector.Zero,
                context.world.camera.originalSize
        )
    }
}
