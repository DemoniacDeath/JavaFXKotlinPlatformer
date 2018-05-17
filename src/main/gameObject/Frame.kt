package main.gameObject

import main.GameContext
import main.GameObject
import main.Rect

class Frame(context: GameContext, frame: Rect, val width: Double) : GameObject(context, frame)
{
    val floor: Solid
    val wallLeft: Solid
    val wallRight: Solid
    val ceiling: Solid

    init {
        ceiling = Solid(context, Rect(
                .0,
                -frame.size.height / 2 + width / 2,
                frame.size.width,
                width
        ))
        addChild(ceiling)
        wallLeft = Solid(context, Rect(
                -frame.size.width / 2 + width / 2,
                .0,
                width,
                frame.size.height - width * 2
        ))
        addChild(wallLeft)
        wallRight = Solid(context, Rect(
                frame.size.width / 2 - width / 2,
                .0,
                width,
                frame.size.height - width * 2
        ))
        addChild(wallRight)
        floor = Solid(context, Rect(
                .0,
                frame.size.height / 2 - width / 2,
                frame.size.width,
                width
        ))
        addChild(floor)
    }
}