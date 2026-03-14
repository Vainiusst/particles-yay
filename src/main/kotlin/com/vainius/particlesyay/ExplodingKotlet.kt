package com.vainius.particlesyay

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
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
    private var movementStarted = false
    private val canvas = Canvas(canvasWidth, canvasHeight)
    private val gc = canvas.graphicsContext2D
    private var timer: AnimationTimer? = null

    override fun start(stage: Stage) {
        val root = StackPane(canvas)
        stage.title = "Exploding Kotlet"
        stage.scene = Scene(root)

        var particle = Particle(x, y, dotSize)

        canvas.setOnMouseClicked {
            if (!movementStarted) {
                movementStarted = true
                startTimer(canvas, gc, particle)
            } else {
                resetState()
                particle = Particle(x, y, dotSize)
                setTheStage(gc, particle)
            }
        }

        stage.show()
        setTheStage(gc, particle)
    }

    private fun resetState() {
        movementStarted = false
        timer?.stop()
        timer = null
        clearCanvas()
    }

    private fun clearCanvas() {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
    }

    private fun setTheStage(gc: GraphicsContext, particle: Particle) {
        particle.draw(gc)
        gc.fill = Color.BLACK
        gc.font = Font.font(16.0)
        gc.fillText(
            "Click your mouse to get the dot moving",
            20.0, canvasHeight - 20.0
        )
    }

    private fun startTimer(
        canvas: Canvas, gc: GraphicsContext, particle: Particle
    ) {
        var lastNanos = 0L
        timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                if (lastNanos == 0L) {
                    lastNanos = now
                    return
                }
                val deltaTime = (now - lastNanos) / 1_000_000_000.0
                lastNanos = now

                particle.move(deltaTime, canvasWidth, canvasHeight)

                // clear and redraw
                clearCanvas()
                particle.draw(gc)
            }
        }
        timer?.start()
    }
}