package main

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import java.util.*

class Game(
        size: Size,
        val exitHandler: () -> Unit
) {
    private val keysPressed: MutableSet<KeyCode> = HashSet()
    private val context: GameContext

    init {
        val gameWidth = 400
        val gameHeight = 300
        val gridSquareSize = 10
        val gravityForce = 0.1
        val itemChance = 0.16

        context = GameContext(size)
    }

    fun keyDown(keyCode: KeyCode) {
        keysPressed.add(keyCode)
        context.world.keyDown(keyCode)
    }

    fun keyUp(keyCode: KeyCode) {
        keysPressed.remove(keyCode)
    }

    fun tick(graphics: GraphicsContext, now: Long) {
        //TODO: Quit
        if (context.quit) {
            exitHandler()
            return
        }

        //TODO: Handle keyboard state

        //TODO?: clean

        //TODO: Process physics

        //TODO: Detect collisions

        //TODO: Animate

        //TODO: Render
        context.world.render(graphics)
        context.ui.render(graphics)
    }
}