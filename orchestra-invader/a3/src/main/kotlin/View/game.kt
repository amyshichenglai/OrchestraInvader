package ui.javafx.mvc.propertiesmvcextended.view

import javafx.animation.AnimationTimer
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.layout.Pane
import javafx.scene.media.AudioClip
import ui.javafx.mvc.propertiesmvcextended.model.*
import kotlin.random.Random

val explosion = AudioClip(Game::class.java.getResource("/sounds/explosion.wav").toString())
val shootsound = AudioClip(Game::class.java.getResource("/sounds/shoot.wav").toString())
val invaderkilled = AudioClip(Game::class.java.getResource("/sounds/invaderkilled.wav").toString())

class Game(private val model: Model) : Pane(), InvalidationListener {
    val enemies = mutableListOf<Enemies>()
    val bullets = mutableListOf<PlayerBullet>()
    val fermatas = mutableListOf<Fermata>()
    val enemybullets = mutableListOf<EnemyBullets>()
    var touchBound = false
    var enemyspeed = 2.0
    var edgereached = false
    var enemyfirerate = ENEMY_FIRE_RATE
    var enemyMovementEnabled = true
    var initialized = false

    private var enemyTimer = 0L

    var gapbwclips = 1000.0
    var clipIndex = 0
    val clips = listOf(fastinvader1, fastinvader2, fastinvader3, fastinvader4)

    val timer = object : AnimationTimer() {
        var lastclipplayed = System.currentTimeMillis()
        var pausetime: Long = 0
        var pauselength: Long = 0

        override fun handle(now: Long) {
            val nowTime = System.currentTimeMillis()
            if (nowTime - lastclipplayed >= gapbwclips) {
                playNextClip()
                lastclipplayed = nowTime
            }

            val bulletiterator = bullets.iterator()
            while (bulletiterator.hasNext()) {
                val bullet = bulletiterator.next()
                animatebullet(bullet)
                if (bullet.toremove) {
                    bulletiterator.remove()
                }
            }

            val fermataiterator = fermatas.iterator()
            while (fermataiterator.hasNext()) {
                val bullet = fermataiterator.next()
                val enemyiterator = enemies.iterator()
                while (enemyiterator.hasNext()) {
                    val enemy = enemyiterator.next()
                    if (enemy.boundsInParent.intersects(bullet.boundsInParent)) {
                        enemyMovementEnabled = false
                        children.remove(bullet)
                        bullet.toremove = true
                        break
                    } else if (bullet.layoutY + bullet.layoutBounds.height < 0) {
                        children.remove(bullet)
                        bullet.toremove = true
                    }
                }
                bullet.layoutY -= bullet.velocity
                if (bullet.toremove) {
                    fermataiterator.remove()
                }

            }

            if (enemyMovementEnabled) {
                enemies.forEach { enemy ->
                    if (enemy.layoutY + enemy.layoutBounds.height > 700) {
                        touchBound = true
                    }
                    if (enemy.toright) {
                        if (enemy.translateX >= 680) {
                            enemy.toright = false
                            enemy.layoutY += ENEMY_VERTICAL_SPEED
                            edgereached = true
                        } else {
                            edgereached = false
                        }
                        enemy.translateX += enemyspeed

                    } else {
                        if (enemy.translateX <= 0) {
                            enemy.toright = true
                            enemy.layoutY += ENEMY_VERTICAL_SPEED
                            edgereached = true
                        } else {
                            edgereached = false
                        }
                        enemy.translateX -= enemyspeed
                    }
                }
            }

            if (!enemyMovementEnabled) {
                if (pausetime == 0L) {
                    pausetime = nowTime
                }
                val elapsedPauseTime = nowTime - pausetime - pauselength
                if (elapsedPauseTime >= 2_000) {
                    enemyMovementEnabled = true
                    pausetime = 0L
                    pauselength = 0L
                }
            }

            if (edgereached) {
                enemyfire()
            }

            val currentTime = System.currentTimeMillis()
            if (!enemies.isEmpty() && currentTime - enemyTimer >= ENEMY_FIRE_RATE) {
                enemyfire()
                enemyTimer = currentTime
            }

            val ebulletiterator = enemybullets.iterator()
            while (ebulletiterator.hasNext()) {
                val bullet = ebulletiterator.next()
                bullet.layoutY += 3
                if (bullet.boundsInParent.intersects(model.player.boundsInParent)) {
                    explosion.play()
                    touchBound = true
                }
            }

            if (enemies.isEmpty() && initialized) {
                winRound()
            }
            if (touchBound) {
                touchBound = false
                lostRound()
            }
        }
    }
    init {
        model.level.addListener(this)
        model.lives.addListener(this)
        invalidated(null)
    }

    override fun invalidated(observable: Observable?) {
        model.gameend.value = false
        children.clear()
        children.add(model.player)
        var enemywidth = 0.0
        for (i in 1..10) {
            val enemy3 = Enemy3().apply {
                layoutX = enemywidth
                layoutY = 0.0
            }
            enemywidth += ENEMY_WIDTH
            children.add(enemy3)
            enemies.add(enemy3)
        }
        enemywidth = 0.0
        for (i in 1..10) {
            val enemy = Enemy2().apply {
                layoutX = enemywidth
                layoutY = 50.0
            }
            enemywidth += ENEMY_WIDTH
            children.add(enemy)
            enemies.add(enemy)
        }
        enemywidth = 0.0
        for (i in 1..10) {
            val enemy = Enemy2().apply {
                layoutX = enemywidth
                layoutY = 100.0
            }
            enemywidth += ENEMY_WIDTH
            children.add(enemy)
            enemies.add(enemy)
        }
        enemywidth = 0.0
        for (i in 1..10) {
            val enemy = Enemy1().apply {
                layoutX = enemywidth
                layoutY = 150.0
            }
            enemywidth += ENEMY_WIDTH
            children.add(enemy)
            enemies.add(enemy)
        }
        enemywidth = 0.0
        for (i in 1..10) {
            val enemy = Enemy1().apply {
                layoutX = enemywidth
                layoutY = 200.0
            }
            enemywidth += ENEMY_WIDTH
            children.add(enemy)
            enemies.add(enemy)
        }
        enemyTimer = System.currentTimeMillis()
        if (model.level.value == 1) {
            enemyfirerate = ENEMY_FIRE_RATE
            enemyspeed = 4.0
        } else if (model.level.value == 2) {
            enemyfirerate = ENEMY_FIRE_RATE*2
            enemyspeed = 5.0
        } else {
            enemyfirerate = ENEMY_FIRE_RATE*3
            enemyspeed = 6.0
        }
        gapbwclips = 1000.0
        model.player.layoutX = Random.nextDouble(0.0, 1150.0)
        initialized = true
    }

    private fun playNextClip() {
        val currentClip = clips[clipIndex]
        currentClip.play()
        clipIndex = (clipIndex + 1) % clips.size
    }
    fun animatebullet(bullet: PlayerBullet) {
        val enemyiterator = enemies.iterator()
        while (enemyiterator.hasNext()) {
            val enemy = enemyiterator.next()
            if (enemy.boundsInParent.intersects(bullet.boundsInParent)) {
                invaderkilled.play()
                children.remove(enemy)
                enemyiterator.remove()
                children.remove(bullet)
                bullet.toremove = true
                if (model.level.value == 1) {
                    model.score.value += 10
                } else if (model.level.value == 2) {
                    model.score.value += 20
                } else {
                    model.score.value += 40
                }
                enemyspeed += 0.07
                gapbwclips -= 10.0
            } else if (bullet.layoutY + bullet.layoutBounds.height < 0) {
                children.remove(bullet)
                bullet.toremove = true
            }
        }
        bullet.layoutY -= bullet.velocity
    }

    fun enemyfire() {
        val randomIndex = Random.nextInt(enemies.size)
        val enemy = enemies[randomIndex]
        val enemybullet = when (enemy) {
            is Enemy1 -> EnemyBullet1()
            is Enemy2 -> EnemyBullet2()
            else -> EnemyBullet3()
        }
        enemybullet.apply {
            layoutX = enemy.layoutX+enemy.translateX
            layoutY = enemy.layoutY+enemy.translateY
        }
        enemybullets.add(enemybullet)
        children.add(enemybullet)
    }

    fun winRound() {
        enemybullets.clear()
        bullets.clear()
        fermatas.clear()
        if (model.level.value == 3) {
            winGame()
        } else {
            model.level.value++
        }
    }

    fun lostRound() {
        fermatas.clear()
        enemies.clear()
        enemybullets.clear()
        bullets.clear()
        model.lives.value--
        if (model.lives.value == 0) {
            lostGame()
        }
    }
    fun lostGame() {
        model.gameend.value = true
        model.win.value = false
        initialized = false
    }

    fun winGame() {
        model.lives.value = 0
        model.gameend.value = true
        model.win.value = true
        initialized = false
    }
}