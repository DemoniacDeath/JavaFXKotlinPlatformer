package main

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyEvent
import javafx.stage.Stage

class App: Application() {
    override fun start(primaryStage: Stage) {
        val width = 800.0
        val height = 600.0
        primaryStage.title = "JavaFX + Kotlin Platformer"

        val root = Group()
        val canvas = Canvas(width, height)
        val gc = canvas.graphicsContext2D
        val game = Game(Size(width, height)) {
            Platform.exit()
            System.exit(0)
        }

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED) {
            game.keyDown(it.code)
        }
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED) {
            game.keyUp(it.code)
        }
        primaryStage.widthProperty().addListener { _, _, newWidth ->
            canvas.width = newWidth.toDouble() - 16//wtf is with these damn canvas borders on windows?
            game.resize(Size(canvas.width, canvas.height))
        }
        primaryStage.heightProperty().addListener { _, _, newHeight ->
            canvas.height = newHeight.toDouble()
            game.resize(Size(canvas.width, canvas.height))
        }

        object : AnimationTimer() {
            override fun handle(now: Long) {
                game.tick(now, gc)
            }
        }.start()

        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.width = width
        primaryStage.height = height
        primaryStage.show()
    }
}