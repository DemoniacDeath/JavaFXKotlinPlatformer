package main

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
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

        object : AnimationTimer() {
            override fun handle(now: Long) {
                game.tick(now, gc)
            }
        }.start()

        //TODO: onresize

        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }
}