package SpaceInvaders

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import ui.javafx.mvc.propertiesmvcextended.model.*
import ui.javafx.mvc.propertiesmvcextended.view.*


fun main(args: Array<String>) {
    Application.launch(SpaceInvaders::class.java, *args)
}
// Used SwitchScenes sample code as starter
class SpaceInvaders : Application() {
    val myModel = Model()
    val game = Game(myModel)
    var scene1: Scene? = null
    var scene2: Scene? = null
    var scene3: Scene? = null

    enum class SCENES {
        SCENE1,
        SCENE2,
        SCENE3
    }

    override fun start(stage: Stage) {
        var prevfire= 0L
        val firelimit = 500_000_000L

        stage.title = "Space Invaders"

        // scene one
        val logo = ImageView(Image("images/logo.png")).apply{
            fitWidth = 700.0
            isPreserveRatio = true
        }
        val label = Text("\nInstructions").apply{
            font = Font.font("Arial", FontWeight.BOLD, 36.0)
        }
        val instruction = Text("ENTER - Start Game\n" +
                "A or leftarrow, D or righarrow - Move ship left or right\n" +
                "SPACE - Fire!\n" +
                "W - Fire fermata to pause enemies\n" +
                "Q - Quit Game\n" +
                "1 or 2 or 3 - Start Game at a specific level\n\n" +
                "by Amy Lai 20873644 (s29lai)"
        ).apply{
            font = Font.font("Arial", FontWeight.BOLD, 20.0)
            textAlignment = TextAlignment.CENTER
        }

        val root1 = VBox(logo, label, instruction).apply{
            alignment = Pos.CENTER
            spacing = 10.0
        }
        root1.background = Background(BackgroundFill(Color.valueOf("#FFEBD1"), CornerRadii.EMPTY, Insets.EMPTY))
        scene1 = Scene(root1, 1200.0, 800.0)

        scene1!!.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.ENTER) {
                myModel.level.value = 1
                myModel.lives.value = 3
                myModel.score.value = 0
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT1) {
                myModel.level.value = 1
                myModel.lives.value = 3
                myModel.score.value = 0
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT2) {
                myModel.level.value = 2
                myModel.lives.value = 3
                myModel.score.value = 0
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT3) {
                myModel.level.value = 3
                myModel.lives.value = 3
                myModel.score.value = 0
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.Q) {
                stage.close()
            }
        }

        // scene two
        val root2 = BorderPane().apply{
            top = Status(myModel)
            center = game

            myModel.gameend.addListener { _, _, newValue ->
                if (newValue == true) {
                    setScene(stage, SCENES.SCENE3)
                }
            }
        }
        root2.background = Background(BackgroundFill(Color.valueOf("#FFEBD1"), CornerRadii.EMPTY, Insets.EMPTY))
        scene2 = Scene(root2, 1200.0, 800.0)

        val pressedKeys = mutableSetOf<KeyCode>()

        scene2!!.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.A) {
                myModel.player.velocity = PLAYER_SPEED * -1
                myModel.player.timer.start()
                pressedKeys.add(KeyCode.A)
            } else if (event.code == KeyCode.D) {
                myModel.player.velocity = PLAYER_SPEED
                myModel.player.timer.start()
                pressedKeys.add(KeyCode.D)
            } else if (event.code == KeyCode.SPACE) {
                val currenttime = System.nanoTime()
                if (currenttime - prevfire >= firelimit) {
                    shootsound.play()
                    val bullet = PlayerBullet()
                    bullet.layoutX = myModel.player.layoutX + myModel.player.fitWidth / 2
                    bullet.layoutY = myModel.player.layoutY
                    (root2.center as Game).bullets.add(bullet)
                    (root2.center as Game).children.add(bullet)
                    prevfire = currenttime
                }
            } else if (event.code == KeyCode.W && myModel.fermatacnt.value > 0) {
                shootsound.play()
                val fermata = Fermata()
                fermata.layoutX = myModel.player.layoutX + myModel.player.fitWidth / 2
                fermata.layoutY = myModel.player.layoutY
                (root2.center as Game).fermatas.add(fermata)
                (root2.center as Game).children.add(fermata)
                myModel.fermatacnt.value--
            } else if (event.code == KeyCode.DIGIT1) {
                if (myModel.level.value != 1) {
                    (root2.center as Game).fermatas.clear()
                    (root2.center as Game).enemies.clear()
                    (root2.center as Game).enemybullets.clear()
                    (root2.center as Game).bullets.clear()
                    (root2.center as Game).initialized = false
                    myModel.level.value = 1
                    myModel.lives.value = 3
                    myModel.score.value = 0
                    myModel.fermatacnt.value = 3
                    setScene(stage, SCENES.SCENE2)
                }
            } else if (event.code == KeyCode.DIGIT2) {
                if (myModel.level.value != 2) {
                    (root2.center as Game).fermatas.clear()
                    (root2.center as Game).enemies.clear()
                    (root2.center as Game).enemybullets.clear()
                    (root2.center as Game).bullets.clear()
                    (root2.center as Game).initialized = false
                    myModel.level.value = 2
                    myModel.lives.value = 3
                    myModel.score.value = 0
                    myModel.fermatacnt.value = 3
                    setScene(stage, SCENES.SCENE2)
                }
            } else if (event.code == KeyCode.DIGIT3) {
                if (myModel.level.value != 3) {
                    (root2.center as Game).fermatas.clear()
                    (root2.center as Game).enemies.clear()
                    (root2.center as Game).enemybullets.clear()
                    (root2.center as Game).bullets.clear()
                    (root2.center as Game).initialized = false
                    myModel.level.value = 3
                    myModel.lives.value = 3
                    myModel.score.value = 0
                    myModel.fermatacnt.value = 3
                    setScene(stage, SCENES.SCENE2)
                }
            }
        }

        scene2!!.onKeyReleased = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.A) {
                pressedKeys.remove(KeyCode.A)
            } else if (event.code == KeyCode.D) {
                pressedKeys.remove(KeyCode.D)
            }

            if (pressedKeys.isEmpty()) {
                myModel.player.timer.stop()
            }
        }

        //scene three
        val lostlabel = Text("\n\nGame Over").apply{
            font = Font.font("Arial", FontWeight.BOLD, 36.0)
            fill = Color.BLACK
        }

        val winlabel = Text("\n\nYou Won!").apply{
            font = Font.font("Arial", FontWeight.BOLD, 36.0)
        }

        val endinstruction = Text("\n\nScore: 0\n" +
                "ENTER - Start Game\n" +
                "I - Back to Instructions!\n" +
                "Q - Quit Game\n" +
                "1 or 2 or 3 - Start Game at a specific level").apply{
            font = Font.font("Arial", FontWeight.BOLD, 20.0)
            textAlignment = TextAlignment.CENTER
        }

        myModel.score.addListener { _, _, newScore ->
            endinstruction.text = "\n\nScore: $newScore\n" +
                    "ENTER - Start Game\n" +
                    "I - Back to Instructions!\n" +
                    "Q - Quit Game\n" +
                    "1 or 2 or 3 - Start Game at a specific level"
        }

        val root3 = VBox().apply{
            alignment = Pos.CENTER
            spacing = 10.0
        }
        root3.children.addAll(lostlabel, endinstruction)
        myModel.win.addListener { _, _, result ->
            if (!result) {
                root3.children.clear()
                root3.children.addAll(lostlabel, endinstruction)
            } else {
                root3.children.clear()
                root3.children.addAll(winlabel, endinstruction)
            }
        }

        root3.background = Background(BackgroundFill(Color.valueOf("#FFEBD1"), CornerRadii.EMPTY, Insets.EMPTY))
        scene3 = Scene(root3, 1200.0, 800.0)

        scene3!!.onKeyPressed = EventHandler { event: KeyEvent ->
            if (event.code == KeyCode.ENTER) {
                myModel.level.value = 1
                myModel.lives.value = 3
                myModel.score.value = 0
                myModel.fermatacnt.value = 3
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT1) {
                myModel.level.value = 1
                myModel.lives.value = 3
                myModel.score.value = 0
                myModel.fermatacnt.value = 3
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT2) {
                myModel.level.value = 2
                myModel.lives.value = 3
                myModel.score.value = 0
                myModel.fermatacnt.value = 3
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.DIGIT3) {
                myModel.level.value = 3
                myModel.lives.value = 3
                myModel.score.value = 0
                myModel.fermatacnt.value = 3
                setScene(stage, SCENES.SCENE2)
            } else if (event.code == KeyCode.I) {
                setScene(stage, SCENES.SCENE1)
            } else if (event.code == KeyCode.Q) {
                stage.close()
            }
        }

        // show starting scene
        setScene(stage, SCENES.SCENE1)
        stage.isResizable = false
        stage.show()
    }

    fun setScene(stage: Stage, scene: SCENES?) {
        when (scene) {
            SCENES.SCENE1 -> {
                stage.scene = scene1
            }

            SCENES.SCENE2 -> {
                stage.scene = scene2
                game.timer.start()
            }

            SCENES.SCENE3 -> {
                stage.scene = scene3
                game.timer.stop()
            }

            else -> {}
        }
    }
}

