package main

import main.gameObject.World
import main.gameObject.ui.UI

data class GameContext(
        var windowSize: Size
) {
    val world = World(this, Rect(0.0, 0.0, windowSize.width / 2, windowSize.height / 2))
    val ui = UI(this, Rect(Vector(), world.camera.originalSize))
    var quit: Boolean = false
}
