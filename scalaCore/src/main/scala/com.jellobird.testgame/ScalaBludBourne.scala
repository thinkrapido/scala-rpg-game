package com.jellobird.testgame

import akka.actor.{Actor, ActorRef, Props}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Game, Gdx}
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.input.InputObserver
import com.jellobird.testgame.maps.Map.MapEnum
import com.jellobird.testgame.maps.{Map, ProxyLocation}
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.Storage.ScreenType._
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.player.{Player, PlayerLocation}
import com.jellobird.testgame.storage.registry.LocationRegistry.Register

import scala.concurrent.duration._

/**
  * Created by jbc on 26.11.16.
  */
class ScalaBludBourne extends Game {

  private val TAG = classOf[ScalaBludBourne].getSimpleName

  private var assetManagerHelper: AssetManager = null

  override def create(): Unit = {
    Storage.assetManager = new AssetManager()

    MapEnum.values.foreach { e => Storage.assetManager.loadMapAsset(e.toString()) }

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
    val playerLocator = Storage.actorSystem.actorOf(Props(new PlayerLocation(x.map.startPosition)))
    Storage.locations ! Register(playerLocator)

    val playerRef = Storage.actorSystem.actorOf(Props(new Player(playerLocator)), "player")
    val locator = Storage.actorSystem.actorOf(Props(new ProxyLocation(playerLocator, x.map)))
    Storage.camera.location = locator
    Storage.locations ! Register(locator)
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