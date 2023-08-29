package ui.javafx.mvc.propertiesmvcextended.view

import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import ui.javafx.mvc.propertiesmvcextended.model.*

class Status(private val model: Model) : AnchorPane(), InvalidationListener {

    init {
        model.level.addListener(this)
        model.score.addListener(this)
        model.lives.addListener(this)
        model.fermatacnt.addListener(this)
        invalidated(null)
        prefHeight = 35.0
    }

    override fun invalidated(observable: Observable?) {
        children.clear()
        children.addAll(
            Text("Score: ${model.score.value}").apply{
                setTopAnchor(this, 5.0)
                setLeftAnchor(this, 50.0)
                font = Font.font("Arial", FontWeight.BOLD, 20.0)
                fill = Color.BLACK
            },
            Text("Fermatas: ${model.fermatacnt.value}").apply{
                setTopAnchor(this, 5.0)
                setLeftAnchor(this, 850.0)
                font = Font.font("Arial", FontWeight.BOLD, 20.0)
                fill = Color.BLACK
            },
            Text("Lives: ${model.lives.value}").apply{
                setTopAnchor(this, 5.0)
                setLeftAnchor(this, 1000.0)
                font = Font.font("Arial", FontWeight.BOLD, 20.0)
                fill = Color.BLACK
            },
            Text("Level: ${model.level.value}").apply{
                setTopAnchor(this, 5.0)
                setLeftAnchor(this, 1100.0)
                font = Font.font("Arial", FontWeight.BOLD, 20.0)
                fill = Color.BLACK
            }
        )
    }
}