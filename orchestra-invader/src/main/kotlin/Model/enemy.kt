package ui.javafx.mvc.propertiesmvcextended.model

import javafx.scene.image.Image
import javafx.scene.image.ImageView

abstract class Enemies : ImageView() {
    var toright = true
}

class Enemy1() : Enemies() {
    init {
        fitWidth = ENEMY_WIDTH
        isPreserveRatio = true
        image = Image("images/enemy1.png")
    }
}

class Enemy2() : Enemies() {
    init {
        fitWidth = ENEMY_WIDTH
        isPreserveRatio = true
        image = Image("images/enemy2.png")
    }
}

class Enemy3() : Enemies() {
    init {
        fitWidth = ENEMY_WIDTH
        isPreserveRatio = true
        image = Image("images/enemy3.png")
    }
}