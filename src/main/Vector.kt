package main

data class Vector(val x: Double, val y: Double) {
    operator fun times(scalar: Double) = Vector(x * scalar, y * scalar)
    operator fun div(scalar: Double) = Vector(x / scalar, y * scalar)
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)
    operator fun minus(v: Vector) = Vector(x - v.x, y - v.y)

    companion object {
        val Zero = Vector(0.0, 0.0)
    }
}