package main.gameObject.ui

import javafx.scene.canvas.GraphicsContext
import main.GameContext
import main.Rect
import main.Vector

class UI(context: GameContext, frame: Rect) : Element(context, frame) {
    val healthBarHolder = Element(context, Rect(-context.world.camera.originalSize.width / 2 + 16, -context.world.camera.originalSize.height / 2 + 2.5, 30.0, 3.0))
    val healthBar = Bar(context, Rect(-context.world.camera.originalSize.width / 2 + 16, -context.world.camera.originalSize.height / 2 + 2.5, 29.0, 2.0))
    val powerBarHolder = Element(context, Rect(context.world.camera.originalSize.width / 2 - 16, -context.world.camera.originalSize.height / 2 + 2.5, 30.0, 3.0))
    val powerBar = Bar(context, Rect(context.world.camera.originalSize.width / 2 - 16, -context.world.camera.originalSize.height / 2 + 2.5, 29.0, 2.0))

    init {
        addChild(healthBarHolder)
        addChild(healthBar)
        addChild(powerBarHolder)
        addChild(powerBar)
    }

    fun render(graphics: GraphicsContext) {
        super.render(
                graphics,
                frame.center,
                Vector(),
                context.world.camera.originalSize
        )
    }
}
