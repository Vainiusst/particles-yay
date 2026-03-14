package com.vainius.particlesyay

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import kotlin.random.Random

class Particle(
    private var x: Double,
    private var y: Double,
    private val width: Double,
) {
    private val colour = MeatballColour().random()
    private var xVelocity = Random.nextDouble(-300.0, 300.0)
    private var yVelocity = Random.nextDouble(-300.0, 300.0)

    fun draw(gc: GraphicsContext) {
        gc.fill = colour
        gc.fillOval(x, y, width, width)
    }

    fun move(deltaTime: Double, canvasWidth: Double, canvasHeight: Double) {
        x += xVelocity * deltaTime
        y += yVelocity * deltaTime
        changeDirIfWallIsHit(canvasWidth, canvasHeight)
    }

    private fun changeDirIfWallIsHit(canvasWidth: Double, canvasHeight: Double) {
        if (x <= 0.0) {
            x = 0.0
            xVelocity = -xVelocity
        } else if (x + width >= canvasWidth) {
            x = canvasWidth - width
            xVelocity = -xVelocity
        }

        if (y <= 0.0) {
            y = 0.0
            yVelocity = -yVelocity
        } else if (y + width >= canvasHeight) {
            y = canvasHeight - width
            yVelocity = -yVelocity
        }
    }
}