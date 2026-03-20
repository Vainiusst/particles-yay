package com.vainius.particlesyay

import javafx.scene.canvas.GraphicsContext
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random

class Particle(
    private var x: Double,
    private var y: Double,
    private val width: Double,
) {
    private val colour = MeatballColour().random()
    private var xVelocity = Random.nextDouble(-350.0, 350.0)
    private var yVelocity = Random.nextDouble(-300.0, -20.0)
    private val gravity = 800

    fun draw(gc: GraphicsContext) {
        gc.fill = colour
        gc.fillOval(x, y, width, width)
    }

    fun move(deltaTime: Double, canvasWidth: Double, canvasHeight: Double) {
        if (dotIsAtTheBottom(canvasHeight)) {
            lowerXVelocity(deltaTime)
        }
        lowerYVelocity(deltaTime)
        x += xVelocity * deltaTime
        y += yVelocity * deltaTime
        changeDirIfWallIsHit(canvasWidth, canvasHeight)
    }

    fun dotIsAtTheBottom(canvasHeight: Double): Boolean {
        return y + width >= canvasHeight - 5.0
    }

    fun lowerXVelocity(deltaTime: Double) {
        if (abs(xVelocity) < 5) {
            xVelocity = 0.0
        } else {
            xVelocity *= 0.5.pow(deltaTime)
        }
    }

    fun lowerYVelocity(deltaTime: Double) {
        if (xVelocity == 0.0) {
            yVelocity = 0.0
        } else {
            yVelocity += gravity * deltaTime
        }
    }

    private fun changeDirIfWallIsHit(canvasWidth: Double, canvasHeight: Double) {
        val hitWallVelocityMultiplier = 0.7
        if (x <= 0.0) {
            x = 0.0
            xVelocity = -xVelocity * hitWallVelocityMultiplier
        } else if (x + width >= canvasWidth) {
            x = canvasWidth - width
            xVelocity = -xVelocity * hitWallVelocityMultiplier
        }

        if (y <= 0.0) {
            y = 0.0
            yVelocity = -yVelocity * hitWallVelocityMultiplier
        } else if (y + width >= canvasHeight) {
            y = canvasHeight - width
            yVelocity = -yVelocity * hitWallVelocityMultiplier
        }
    }
}
