package com.vainius.particlesyay

import javafx.scene.paint.Color

class MeatballColour {
    private val palette = listOf(
        Color.web("#7A2E18"),
        Color.web("#A43B1E"),
        Color.web("#C85A2D"),
        Color.web("#E07A3A")
    )

    fun random(): Color {
        return palette.random()
    }
}