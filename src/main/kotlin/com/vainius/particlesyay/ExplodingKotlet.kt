package com.vainius.particlesyay

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage

class ExplodingKotlet : Application() {
    private val canvasWidth = 900.0
    private val canvasHeight = 600.0
    private val dotSize = 12.0
    private var x = 450.0
    private var y = 300.0
    private var vx = -90.0   // pixels per second
    private var vy = -90.0
    private var meatballColour = MeatballColour()
    private var movementStarted = false

    override fun start(stage: Stage) {
        val canvas = Canvas(canvasWidth, canvasHeight)
        val root = StackPane(canvas)
        stage.title = "Exploding Kotlet"
        stage.scene = Scene(root)

        val gc = canvas.graphicsContext2D
        val dotColour = meatballColour.random()
        gc.fill = dotColour
        canvas.setOnMouseClicked {
            if(!movementStarted) {
                movementStarted = true
                gc.fill = dotColour
                startTimer(canvas, gc)
            }
        }
        stage.show()
        gc.fillOval(x, y, dotSize, dotSize)
        gc.fill = Color.BLACK
        gc.font = Font.font(16.0)
        gc.fillText("Click your mouse to get the ball moving", 20.0, canvasHeight - 20.0)
    }

    private fun startTimer(canvas: Canvas, gc: GraphicsContext) {
        var lastNanos = 0L
        val timer = object : javafx.animation.AnimationTimer() {
            override fun handle(now: Long) {
                if (lastNanos == 0L) {
                    lastNanos = now
                    return
                }
                val dt = (now - lastNanos) / 1_000_000_000.0
                lastNanos = now

                x += vx * dt
                y += vy * dt
                changeDirIfWallIsHit(canvas)

                // clear
                gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
                gc.fillOval(x, y, dotSize, dotSize)
            }
        }
        timer.start()
    }

    private fun changeDirIfWallIsHit(canvas: Canvas) {
        if (x <= 0.0) {
            x = 0.0
            vx = -vx
        } else if (x + dotSize >= canvas.width) {
            x = canvas.width - dotSize
            vx = -vx
        }

        if (y <= 0.0) {
            y = 0.0
            vy = -vy
        } else if (y + dotSize >= canvas.height) {
            y = canvas.height - dotSize
            vy = -vy
        }
    }
}