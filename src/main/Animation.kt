package main

import javafx.scene.image.Image
import javafx.scene.image.WritableImage

private const val speedScale = 100000

class Animation (
        val speed: Int,
        val frames: MutableList<RenderObject> = mutableListOf()
) {
    var startTick = 0L
    var turnedLeft = false
    set (value) {
        field = value

        frames.forEach {
            it.flipped = value
        }
    }

    fun animate(ticks: Long): RenderObject {
        if (startTick == 0L || ticks - startTick >= frames.size * speed * speedScale) startTick = ticks
        return frames[((ticks - startTick) / (speed * speedScale)).toInt()]
    }

    companion object {
        fun withSingleRenderObject(renderObject: RenderObject) = Animation(
                1, mutableListOf(renderObject)
        )

        fun withSpeedAndImage(speed: Int, image: Image, width: Int, height: Int, framesNumber: Int): Animation {
            val animation = Animation(speed)
            val reader = image.pixelReader
            for (i in 0 until framesNumber) {
                animation.frames.add(RenderObject(WritableImage(reader, 0, i * height, width, height)))
            }
            return animation
        }
    }
}
