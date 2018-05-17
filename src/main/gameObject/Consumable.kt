package main.gameObject

import main.GameContext
import main.GameObject
import main.PhysicsState
import main.Rect

class Consumable(context: GameContext, frame: Rect) : GameObject(context, frame) {
    init {
        physics = PhysicsState(this)
    }
}
