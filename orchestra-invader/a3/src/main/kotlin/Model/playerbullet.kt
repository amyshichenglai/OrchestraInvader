package ui.javafx.mvc.propertiesmvcextended.model

import javafx.scene.image.Image
import javafx.scene.image.ImageView

class PlayerBullet() : ImageView() {
    var velocity = 3
    var toremove = false

    init {
        fitWidth = 30.0
        isPreserveRatio = true
        image = Image("images/player_bullet.png")

    }
}

class Fermata(): ImageView() {
    var velocity = 3
    var toremove = false

    init {
        fitWidth = 30.0
        isPreserveRatio = true
        image = Image("images/fermata.png")

    }
}

