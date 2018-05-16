package main.gameObject

import javafx.scene.input.KeyCode
import main.*

class Player (
        context: GameContext,
        frame: Rect,
        val idleAnimation: Animation,
        val moveAnimation: Animation,
        val jumpAnimation: Animation,
        val crouchAnimation: Animation,
        val crouchMoveAnimation: Animation
): GameObject(context, frame) {
    var speed: Double = 0.0
    var jumpSpeed: Double = 0.0
    var power: Int = 0
    var maxPower: Int = 0
    var jumped: Boolean = false
    var health: Int = 100
    var dead: Boolean = false
    var won: Boolean = false
    var crouched: Boolean = false
        set(value) {
            if (value && !field) {
                field = true
                frame.center.y += originalSize.height / 4
                frame.size.height = originalSize.height / 2
            } else if (!value && field) {
                field = false
                frame.center.y -= originalSize.height / 4
                frame.size.height = originalSize.height
            }
        }
    val originalSize: Size

    init {
        physics = PhysicsState(this)
        physics?.gravity = true
        physics?.still = false
        originalSize = frame.size.copy()
    }

    override fun keyDown(key: KeyCode) {
        when (key) {
            KeyCode.G -> {
                physics?.gravity = physics?.gravity != true
                if (physics?.gravity != true) {
                    jumped = true
                    physics?.velocity = Vector()
                }
            }
        }
        super.keyDown(key)
    }

    override fun handleKeyboardState(keys: Set<KeyCode>) {
        if (!dead) {
            var sitDown = false
            var moveLeft = false
            var moveRight = false
            var moveVector = Vector()
            if (keys.contains(KeyCode.LEFT) ||
                    keys.contains(KeyCode.A)) {
                moveVector.x -= speed
                moveLeft = true
            }
            if (keys.contains(KeyCode.RIGHT) ||
                    keys.contains(KeyCode.D)) {
                moveVector.x += speed
                moveRight = true
            }
            if (keys.contains(KeyCode.UP) ||
                    keys.contains(KeyCode.W) ||
                    keys.contains(KeyCode.SPACE)) {
                if (physics?.gravity != true) {
                    moveVector.y -= speed
                } else if (!jumped) {
                    physics?.velocity?.y = ((physics?.velocity?.y ?: 0.0) - jumpSpeed)
                    jumped = true
                }
            }
            if (keys.contains(KeyCode.DOWN) ||
                    keys.contains(KeyCode.S) ||
                    keys.contains(KeyCode.CONTROL)) {
                if (physics?.gravity != true) {
                    moveVector.y += speed
                } else {
                    sitDown = true
                }
            }
            crouched = sitDown

//            if (moveLeft && !moveRight) {
//                moveAnimation.turnedLeft = true
//                crouchAnimation.turnedLeft = true
//                crouchMoveAnimation.turnedLeft = true
//            }
//            if (moveRight && !moveLeft) {
//                moveAnimation.turnedLeft = false
//                crouchAnimation.turnedLeft = false
//                crouchMoveAnimation.turnedLeft = false
//            }
            animation = when {
                (!moveLeft && !moveRight && !jumped && !crouched) -> idleAnimation
                (!moveLeft && !moveRight && !jumped && crouched) -> crouchAnimation
                ((moveLeft || moveRight) && !jumped && !crouched) -> moveAnimation
                ((moveLeft || moveRight) && !jumped && crouched) -> crouchMoveAnimation
                (jumped && crouched) -> crouchAnimation
                (jumped && !crouched) -> jumpAnimation
                else -> animation
            }
            frame.center += moveVector
        }
        super.handleKeyboardState(keys)
    }

    override fun handleEnterCollision(collision: Collision) {
        val consumable = collision.collider
        if (consumable is Consumable) {
            power += 1
//            context.ui.powerBar.value = power/maxPower*100.0
            consumable.removed = true
            speed += 0.01
            jumpSpeed += 0.01
            if (power >= maxPower) {
                win()
            }
        }
    }

    override fun handleExitCollision(collider: GameObject) {
        if (physics?.colliders?.count() == 0) {
            jumped = true
        }
    }

    override fun handleCollision(collision: Collision) {
        if (Math.abs(collision.collisionVector.x) > Math.abs(collision.collisionVector.y)) {
            if (collision.collisionVector.y > 0 && jumped && physics?.gravity == true) {
                jumped = false
            }
        }
    }

    fun dealDamage(damage: Int) {
        if (!won) {
            health -= damage
//            context.ui.healthBar.value = health
            if (health < 0) {
                die()
            }
        }
    }

    private fun die() {
        dead = true
//        context.ui.deathText.visible = true
    }

    private fun win() {
        won = true
//        context.ui.winText.visible = true
    }


}
