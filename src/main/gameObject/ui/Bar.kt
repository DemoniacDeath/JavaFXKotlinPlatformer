package main.gameObject.ui

import main.GameContext
import main.Rect

class Bar(context: GameContext, frame: Rect): Element(context, frame) {
    private var originalFrame = Rect(center = frame.center.copy(), size = frame.size.copy())
    var value = 100.0
    set(_value) {
        var value = _value
        if (value > 100) value = 100.0
        if (value < 0)  value = 0.0
        field = value

        redrawValue()
    }

    override var frame: Rect = frame
    set(value) {
        field = value
        originalFrame = Rect(center = value.center.copy(), size = value.size.copy())
        redrawValue()
    }

    private fun redrawValue() {
        frame.center.x = originalFrame.center.x + originalFrame.size.width * ((value - 100) / 200)
        frame.size.width = originalFrame.size.width / 100 * value
    }
}