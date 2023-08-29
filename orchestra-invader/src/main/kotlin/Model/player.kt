package ui.javafx.mvc.propertiesmvcextended.model

import javafx.animation.AnimationTimer
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class Player() : ImageView() {
    var velocity = PLAYER_SPEED
    val timer: AnimationTimer = object : AnimationTimer() {
        override fun handle(now: Long) {
            if (layoutX + velocity in 0.0..1150.0) {
                layoutX += velocity
            }
        }
    }
    init {
        fitWidth = 50.0
        isPreserveRatio = true
        image = Image("images/player.png")
    }
}