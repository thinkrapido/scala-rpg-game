package com.mygdx.game.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.jellobird.testgame.ScalaBludBourne
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object DesktopLauncher {
  def main(arg: Array[String]) {
    val config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    config.title = "Scala BludBourne"
    config.useGL30 = false
    config.width = 1800
    config.height = 1600
    val app: Application = new LwjglApplication(new ScalaBludBourne, config)
    Gdx.app = app
    Gdx.app.setLogLevel(Application.LOG_DEBUG)
  }
}