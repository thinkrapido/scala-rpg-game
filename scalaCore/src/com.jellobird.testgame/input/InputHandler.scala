package com.jellobird.testgame.input

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.screen.GameScreen

/**
  * Created by jbc on 29.11.16.
  */
final class InputHandler(private val screen: GameScreen) extends InputAdapter {
  require(screen != null)

  val xInc = 1
  val yInc = 1

  override def keyDown(keycode: Int): Boolean = {
    super.keyDown(keycode)

    if (keycode == Keys.RIGHT) {
      Storage.camera.position.add(xInc, 0, 0)
    }
    if (keycode == Keys.LEFT) {
      Storage.camera.position.add(-xInc, 0, 0)
    }
    if (keycode == Keys.UP) {
      Storage.camera.position.add(0, yInc, 0)
    }
    if (keycode == Keys.DOWN) {
      Storage.camera.position.add(0, -yInc, 0)
    }

    println(Storage.camera.position)

    return true
  }

}
