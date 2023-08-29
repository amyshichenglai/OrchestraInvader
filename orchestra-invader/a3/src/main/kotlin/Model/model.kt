package ui.javafx.mvc.propertiesmvcextended.model

import javafx.beans.property.*
import javafx.scene.media.AudioClip
import kotlin.random.Random

const val PLAYER_SPEED = 5.0
const val ENEMY_VERTICAL_SPEED = 25.0
const val ENEMY_WIDTH = 50.0
const val ENEMY_FIRE_RATE = 2000L
val fastinvader1 = AudioClip(Model::class.java.getResource("/sounds/fastinvader1.wav").toString())
val fastinvader2 = AudioClip(Model::class.java.getResource("/sounds/fastinvader2.wav").toString())
val fastinvader3 = AudioClip(Model::class.java.getResource("/sounds/fastinvader3.wav").toString())
val fastinvader4 = AudioClip(Model::class.java.getResource("/sounds/fastinvader4.wav").toString())
class Model {

    val level = SimpleIntegerProperty(1)
    val lives = SimpleIntegerProperty(3)
    val score = SimpleIntegerProperty(0)
    val fermatacnt = SimpleIntegerProperty(3)
    val gameend = SimpleBooleanProperty(false)
    val win = SimpleBooleanProperty(false)

    val player = Player().apply {
        layoutX = Random.nextDouble(0.0, 1150.0)
        layoutY = 700.0
    }

}
