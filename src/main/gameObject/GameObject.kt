package main.gameObject

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import main.*

abstract class GameObject(
        val context: GameContext,
        var frame: Rect
) {
    private val children: MutableList<GameObject> = ArrayList()

    var parent: GameObject? = null
    var renderObject: RenderObject? = null
    var physics: PhysicsState? = null
    var animation: Animation? = null
    var visible: Boolean = true
    var removed: Boolean = false

    open fun handleKeyboardState(keys: Set<KeyCode>) {
        for (child in children) child.handleKeyboardState(keys)
    }

    open fun keyDown(key: KeyCode) {
        for (child in children) child.keyDown(key)
    }

    open fun handleEnterCollision(collision: Collision) {}
    open fun handleExitCollision(collider: GameObject) {}
    open fun handleCollision(collision: Collision) {}

    fun animate(now: Long) {
        //TODO
//        renderObject = animation?.animate(now) ?: renderObject
        for (child in children) child.animate(now)
    }

    open fun render(
            graphics: GraphicsContext,
            localBasis: Vector,
            cameraPosition: Vector,
            cameraSize: Size
    ) {
        if (visible) {
            renderObject?.render(
                    graphics,
                    context,
                    frame.center + localBasis,
                    frame.size,
                    cameraPosition,
                    cameraSize
            )
        }
        for (child in children) child.render(
                graphics,
                frame.center + localBasis,
                cameraPosition,
                cameraSize
        )
    }

    fun processPhysics() {
        physics?.change()
        for (child in children) child.processPhysics()
    }

    fun detectCollisions() {
        val allColliders = ArrayList<GameObject>()
        detectCollisions(allColliders)
        val size = allColliders.size
        for (i in 0 until size) {
            for (j in i+1 until size) {
                allColliders[i].physics?.detectCollisions(allColliders[j].physics)
            }
        }
    }

    private fun detectCollisions(allColliders: MutableList<GameObject>) {
        if (physics != null) allColliders.add(this)

        for (child in children) child.detectCollisions(allColliders)
    }

    fun addChild(child: GameObject) {
        children.add(child)
        child.parent = this
    }

    fun clean() {
        for (child in children) child.physics?.clean()
        children.removeAll { it.removed }
    }

    fun globalPosition(): Vector {
        return frame.center + (parent?.globalPosition() ?: Vector())
    }
}
