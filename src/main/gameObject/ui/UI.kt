package main.gameObject.ui

import javafx.scene.canvas.GraphicsContext
import main.GameContext
import main.Rect
import main.Size
import main.Vector

class UI(context: GameContext, frame: Rect) : Element(context, frame) {
    val healthBarHolder = Element(context, Rect(-frame.size.width / 2 + 16, -frame.size.height / 2 + 2.5, 30.0, 3.0))
    val healthBar = Bar(context, Rect(-frame.size.width / 2 + 16, -frame.size.height / 2 + 2.5, 29.0, 2.0))
    val powerBarHolder = Element(context, Rect(frame.size.width / 2 - 16, -frame.size.height / 2 + 2.5, 30.0, 3.0))
    val powerBar = Bar(context, Rect(frame.size.width / 2 - 16, -frame.size.height / 2 + 2.5, 29.0, 2.0))
    val deathText = Text(context, Rect(0.0, 0.0, 100.0, 15.0))
    val winText = Text(context, Rect(0.0, 0.0, 100.0, 15.0))

    init {
        addChild(healthBarHolder)
        addChild(healthBar)
        addChild(powerBarHolder)
        addChild(powerBar)
        addChild(deathText)
        addChild(winText)
    }

    fun render(graphics: GraphicsContext) {
        super.render(
                graphics,
                frame.center,
                Vector(),
                context.world.camera.originalSize
        )
    }

    override fun onWindowResize(newSize: Size) {
        frame.size = Size(newSize.width/4, newSize.height/4)
        healthBarHolder.frame = Rect(-frame.size.width / 2 + 16, -frame.size.height / 2 + 2.5, 30.0, 3.0)
        healthBar.frame = Rect(-frame.size.width / 2 + 16, -frame.size.height / 2 + 2.5, 29.0, 2.0)
        powerBarHolder.frame = Rect(frame.size.width / 2 - 16, -frame.size.height / 2 + 2.5, 30.0, 3.0)
        powerBar.frame = Rect(frame.size.width / 2 - 16, -frame.size.height / 2 + 2.5, 29.0, 2.0)
        super.onWindowResize(newSize)
    }
}
