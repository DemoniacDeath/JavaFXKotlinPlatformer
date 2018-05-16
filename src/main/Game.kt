package main

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import main.gameObject.Player
import java.util.*
import main.gameObject.Solid



class Game(
        size: Size,
        val exitHandler: () -> Unit
) {
    private val keysPressed: MutableSet<KeyCode> = HashSet()
    private val context: GameContext

    init {
        val gameWidth = 400.0
        val gameHeight = 300.0
        val gridSquareSize = 10.0
        val gravityForce = 0.1
        val itemChance = 0.16

        context = GameContext(size)
        context.world.frame.size = Size(gameWidth, gameHeight)

        val player = Player(
                context,
                Rect(
                        0.0,
                        0.0,
                        gridSquareSize,
                        gridSquareSize * 2
                ),
                Animation(),
                Animation(),
                Animation(),
                Animation(),
                Animation()
        )
        player.speed = 1.3
        player.jumpSpeed = 2.5
        player?.physics?.gravityForce = gravityForce
        player?.physics?.gravity = false
        player.addChild(context.world.camera)
        player.renderObject = RenderObject.fromColor(Color.BLACK)
        context.world.addChild(player)

        val rnd = Random()
        val count = (context.world.frame.size.width * context.world.frame.size.height * itemChance / (gridSquareSize * gridSquareSize)).toInt()
        var powerCount = count / 2
//        player.MaxPower = powerCount
        val x = (context.world.frame.size.width / gridSquareSize - 2).toInt()
        val y = (context.world.frame.size.height / gridSquareSize - 2).toInt()
        var rndX: Int
        var rndY: Int
        val takenX = IntArray(count)
        val takenY = IntArray(count)
        for (i in 0 until count) {
            var taken: Boolean
            do {
                taken = false
                rndX = rnd.nextInt() % x
                rndY = rnd.nextInt() % y
                for (j in 0..i) {
                    if (rndX == takenX[j] && rndY == takenY[j]) {
                        taken = true
                        break
                    }
                }
            } while (taken)

            takenX[i] = rndX
            takenY[i] = rndY

            val rect = Rect(
                    context.world.frame.size.width / 2 - gridSquareSize * 1.5 - rndX * gridSquareSize,
                    context.world.frame.size.height / 2 - gridSquareSize * 1.5 - rndY * gridSquareSize,
                    gridSquareSize,
                    gridSquareSize)

            if (powerCount > 0) {
//                val gameObject = Consumable(context, rect)
//                gameObject.RenderObject = RenderObject.fromColor(Color.GREEN)
//                context.world.AddChild(gameObject)
                powerCount--

            } else {
                val gameObject = Solid(context, rect)
//                gameObject.renderObject = RenderObject(Properties.Resources.brick)
                gameObject.renderObject = RenderObject.fromColor(Color.RED)
                context.world.addChild(gameObject)
            }
        }

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
        context.world.handleKeyboardState(keysPressed)

        //TODO?: clean
        graphics.clearRect(0.0, 0.0, context.windowSize.width, context.windowSize.height)

        //TODO: Process physics

        //TODO: Detect collisions

        //TODO: Animate

        //TODO: Render
        context.world.render(graphics)
        context.ui.render(graphics)
    }
}