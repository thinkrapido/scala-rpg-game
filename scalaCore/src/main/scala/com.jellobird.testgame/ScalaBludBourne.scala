package com.jellobird.testgame

import akka.actor.Props
import com.badlogic.gdx.{Game, Gdx}
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.input.InputObserver
import com.jellobird.testgame.maps.Map.MapEnum
import com.jellobird.testgame.maps.{ProxyPosition, Tile}
import com.jellobird.testgame.player.{Player, PlayerPosition}
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.Storage.ScreenType._
import com.jellobird.testgame.visuals.Visual.SpriteMap

/**
  * Created by jbc on 26.11.16.
  */
class ScalaBludBourne extends Game {

  private val TAG = classOf[ScalaBludBourne].getSimpleName

  private var assetManagerHelper: AssetManager = null

  override def create(): Unit = {
    Storage.assetManager = new AssetManager()

    MapEnum.values.foreach { e => Storage.assetManager.loadMapAsset(e.toString()) }
    SpriteMap.values.foreach { e => Storage.assetManager.loadTextureAsset(e.toString()) }

    Storage.createScreens()
    Storage.screens.get(MainScreen) match {
      case Some(x: GameScreen) =>
        Storage.currentScreen = x
        setScreen(x)
        Gdx.app.debug(TAG, "screen set")
        setPlayerOnMap(x)
      case None =>
        Gdx.app.debug(TAG, "screen not set")
      case _ =>
    }

  }

  def setPlayerOnMap(x: GameScreen) = {
    val playerLocator = Storage.actorSystem.actorOf(Props(new PlayerPosition(x.map.startPosition, new Tile(1, 1))))
    //playerLocator ! SetDestination(null, "next", x.map.startPosition)

    val playerRef = Storage.actorSystem.actorOf(Props(new Player(playerLocator)), "player")
    val locator = Storage.actorSystem.actorOf(Props(new ProxyPosition(playerLocator, x.map)))
    Storage.camera.pos = locator
    ScalaBludBourne.inputObserver.addListener(playerRef)
  }

  override def dispose(): Unit = {
    Storage.dispose()
  }

  override def pause(): Unit = {
    super.pause()
    assetManagerHelper = Storage.assetManager
    Storage.assetManager = null
  }

  override def resume(): Unit = {
    super.resume()
    Storage.assetManager = assetManagerHelper
    assetManagerHelper = null
  }

}

object ScalaBludBourne {

  val inputObserver = new InputObserver()

}