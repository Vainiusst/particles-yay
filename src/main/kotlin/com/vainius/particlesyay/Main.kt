package com.vainius.particlesyay

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Main : Application() {
    private val dotSize = 12.0
    private var x = 500.0
    private var y = 300.0
    private var vx = -40.0   // pixels per second
    private var vy = -75.0

    override fun start(stage: Stage) {
        val canvas = Canvas(900.0, 600.0)
        val root = StackPane(canvas)

        stage.title = "Exploding Kotlet"
        stage.scene = Scene(root)
        val gc = canvas.graphicsContext2D
        stage.show()
        timer(canvas, gc)
    }

    private fun timer(canvas: Canvas, gc: GraphicsContext) {
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

                // draw dot
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

fun main() {
    Application.launch(Main::class.java)
}