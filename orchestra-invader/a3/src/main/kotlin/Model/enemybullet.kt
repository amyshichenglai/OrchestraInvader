package ui.javafx.mvc.propertiesmvcextended.model

import javafx.scene.image.Image
import javafx.scene.image.ImageView

abstract class EnemyBullets : ImageView() {
}
class EnemyBullet1 : EnemyBullets() {
    init {
        fitWidth = 40.0
        isPreserveRatio = true
        image = Image("images/bullet1.png")
    }
}

class EnemyBullet2 : EnemyBullets() {
    init {
        fitWidth = 40.0
        isPreserveRatio = true
        image = Image("images/bullet2.png")
    }
}

class EnemyBullet3 : EnemyBullets() {
    init {
        fitWidth = 40.0
        isPreserveRatio = true
        image = Image("images/bullet3.png")
    }
}
