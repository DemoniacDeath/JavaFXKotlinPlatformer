package main

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import main.gameObject.Consumable
import main.gameObject.Frame
import main.gameObject.Player
import main.gameObject.Solid
import java.util.*

private const val tickThreshold: Long = 10000000L

class Game(
        size: Size,
        val exitHandler: () -> Unit
) {
    private val keysPressed: MutableSet<KeyCode> = HashSet()
    private val context: GameContext
    private var lastTick = 0L

    init {
        val gridSquareSize = 10.0
        val gravityForce = 0.1
        val itemChance = 0.16

        context = GameContext(size)

        val player = Player(
                context,
                Rect(
                        0.0,
                        0.0,
                        gridSquareSize,
                        gridSquareSize * 2
                ),
                Animation.withSingleRenderObject(RenderObject(Image("/img/idle.png"))),
                Animation.withSingleRenderObject(RenderObject(Image("/img/jump.png"))),
                Animation.withSingleRenderObject(RenderObject(Image("/img/crouch.png"))),
                Animation.withSingleRenderObject(RenderObject(Image("/img/crouch.png"))),
                Animation.withSpeedAndImage(1000, Image("/img/move.png"), 40, 80, 6)
        )
        player.speed = 1.3
        player.jumpSpeed = 2.5
        player.physics?.gravityForce = gravityForce
        player.physics?.gravity = true
        player.addChild(context.world.camera)
        player.renderObject = RenderObject.fromColor(Color.BLACK)
        context.world.addChild(player)

        val frame = Frame(context, Rect(
                .0, .0,
                context.world.frame.size.width,
                context.world.frame.size.height
        ), gridSquareSize)
        frame.ceiling.renderObject = RenderObject.fromColor(Color.BLACK)
        frame.wallLeft.renderObject = RenderObject.fromColor(Color.BLACK)
        frame.wallRight.renderObject = RenderObject.fromColor(Color.BLACK)
        frame.floor.renderObject = RenderObject.fromColor(Color.BLACK)
        context.world.addChild(frame)

        val rnd = Random()
        val count = (context.world.frame.size.width * context.world.frame.size.height * itemChance / (gridSquareSize * gridSquareSize)).toInt()
        var powerCount = count / 2
        player.maxPower = powerCount
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
                rndX = rnd.nextInt(x)
                rndY = rnd.nextInt(y)
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
                val gameObject = Consumable(context, rect)
                gameObject.renderObject = RenderObject.fromColor(Color.GREEN)
                context.world.addChild(gameObject)
                powerCount--
            } else {
                val gameObject = Solid(context, rect)
                gameObject.renderObject = RenderObject(Image("/img/brick.png"))
                context.world.addChild(gameObject)
            }
        }

        context.ui.healthBarHolder.renderObject = RenderObject.fromColor(Color.BLACK)
        context.ui.healthBar.renderObject = RenderObject.fromColor(Color.RED)
        context.ui.powerBarHolder.renderObject = RenderObject.fromColor(Color.BLACK)
        context.ui.powerBar.renderObject = RenderObject.fromColor(Color.GREEN)
        context.ui.deathText.text = "You died! Game Over!"
        context.ui.deathText.font = Font(25.0)
        context.ui.deathText.color = Color.RED
        context.ui.deathText.visible = false
        context.ui.winText.text = "Congratulations! You won!"
        context.ui.winText.font = Font(25.0)
        context.ui.winText.color = Color.GREEN
        context.ui.winText.visible = false

    }

    fun keyDown(keyCode: KeyCode) {
        keysPressed.add(keyCode)
        context.world.keyDown(keyCode)
    }

    fun keyUp(keyCode: KeyCode) {
        keysPressed.remove(keyCode)
    }

    fun tick(ticks: Long, graphics: GraphicsContext) {
        if (ticks - lastTick < tickThreshold) {
            return
        }
        lastTick = ticks

        if (context.quit) {
            exitHandler()
            return
        }

        context.world.handleKeyboardState(keysPressed)

        context.world.clean()

        context.world.processPhysics()

        context.world.detectCollisions()

        context.world.animate(ticks)

        graphics.clearRect(0.0, 0.0, context.windowSize.width, context.windowSize.height)
        context.world.render(graphics)
        context.ui.render(graphics)
    }

    fun resize(size: Size) {
        context.windowSize = size
        context.world.onWindowResize(size)
        context.ui.onWindowResize(size)
    }
}