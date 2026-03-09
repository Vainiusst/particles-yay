package com.vainius.particlesyay

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(900.0, 600.0)
        val root = StackPane(canvas)

        stage.title = "Exploding Kotlet"
        stage.scene = Scene(root)
        stage.show()
    }
}

fun main() {
    Application.launch(Main::class.java)
}