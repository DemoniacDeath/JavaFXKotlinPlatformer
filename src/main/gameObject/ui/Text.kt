package main.gameObject.ui

import javafx.scene.Scene
import javafx.scene.SnapshotParameters
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import main.GameContext
import main.Rect
import main.RenderObject

class Text(context: GameContext, frame: Rect): Element(context, frame) {
    var text: String = ""
    set(value) {
        field = value
        generate()
    }

    var color: Color = Color.BLACK
    set(value) {
        field = value
        generate()
    }

    var font: Font? = null
    set(value) {
        field = value
        generate()
    }

    fun generate() {
        if (this.text.isNotEmpty()) {
            val label = Label(this.text)
            if (this.font != null) {
                label.font = this.font
            }
            label.textFill = this.color
            val pane = StackPane(label)
            val scene = Scene(pane)
            val sp = SnapshotParameters()
            sp.fill = Color.TRANSPARENT
            val textImage = label.snapshot(sp, null)
            this.renderObject = RenderObject(textImage)
        }
    }
}
