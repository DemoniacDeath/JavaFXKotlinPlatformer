package main.gameObject

import main.*

class Solid(context: GameContext, frame: Rect) : GameObject(context, frame) {
    init {
        physics = PhysicsState(this)
    }

    override fun handleEnterCollision(collision: Collision) {
//        val player = collision.collider
//        when (player) {
//            is Player -> {
//                if (collision.collider.physics?.velocity.y > 5) {
////HURT HIM!!!
//        player.DealDamage(Math.Round(collision.Collider.Physics.Velocity.Y * 10) as Int)
//                }
//            }
//        }
    }

    override fun handleCollision(collision: Collision) {
        if (Math.abs(collision.collisionVector.x) < Math.abs(collision.collisionVector.y)) {
            collision.collider.frame.center.x += collision.collisionVector.x
            collision.collider.physics?.velocity?.x = 0.0
        } else {
            collision.collider.frame.center.y += collision.collisionVector.y
            collision.collider.physics?.velocity?.y = 0.0
        }
    }
}
