package main.gameObject

import main.GameContext
import main.GameObject
import main.Rect

class Frame(context: GameContext, frame: Rect, val width: Double) : GameObject(context, frame)
{
    val floor: Solid = Solid(context, Rect(
            .0,
            frame.size.height / 2 - width / 2,
            frame.size.width,
            width
    ))
    val wallLeft: Solid = Solid(context, Rect(
            -frame.size.width / 2 + width / 2,
            .0,
            width,
            frame.size.height - width * 2
    ))
    val wallRight: Solid = Solid(context, Rect(
            frame.size.width / 2 - width / 2,
            .0,
            width,
            frame.size.height - width * 2
    ))
    val ceiling: Solid = Solid(context, Rect(
            .0,
            -frame.size.height / 2 + width / 2,
            frame.size.width,
            width
    ))

    init {
        addChild(floor)
        addChild(wallLeft)
        addChild(wallRight)
        addChild(ceiling)
    }
}