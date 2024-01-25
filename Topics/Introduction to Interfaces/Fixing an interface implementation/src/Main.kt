// Do not change the code below.

data class Vector2(var x: Int, var y: Int)

interface PhysicalBody2D {
    val affectedByGravity: Boolean
        get() = true
    val simulated: Boolean
        get() = true

    val mass: Double
    val gravityStrength: Double

    val velocity: Vector2

    fun simulate()
    fun stopSimulation()
    fun setVelocityToZero()
}

// Do not change the code above.

/*
    Physical body with a mass of 0.25,
    Velocity of 0,
    And no additional gravity strength
*/
class Stone : PhysicalBody2D {
    override val mass: Double
        get() = 0.25

    override val gravityStrength: Double
        get() = 1.0

    override val velocity: Vector2 = TODO()

    override fun simulate() {
        // Some code, which you don't need to implement
    }

    override fun stopSimulation() {
        // Some code, which you don't need to implement
    }
    // Write any lacking code here.

    override fun setVelocityToZero() {
        TODO("Not yet implemented")
    }
}