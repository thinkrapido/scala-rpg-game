package com.jellobird.testgame

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Game, Gdx}
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.maps.LocationsRepository.AbsolutePos
import com.jellobird.testgame.maps.Map.MapEnum
import com.jellobird.testgame.maps.RandomLocator
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.Storage.ScreenType._
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.maps.Map
import scala.concurrent.duration._

/**
  * Created by jbc on 26.11.16.
  */
class ScalaBludBourne extends Game {

  private val TAG = classOf[ScalaBludBourne].getSimpleName

  private var assetManagerHelper: AssetManager = null

  class RandomPosGenerator(val uuid: String, val actor: ActorRef, val map: Map) {

    import Storage.actorSystem.dispatcher
    Storage.actorSystem.scheduler.schedule(2.seconds, 1.seconds) {
      tick
    }

    def tick = {
      val v = new Vector2(
        (map.width * Math.random()).asInstanceOf[Float],
        (map.height * Math.random()).asInstanceOf[Float]
      )
      actor ! new AbsolutePos(v, uuid)
    }

  }

  var randomPosGenerator: RandomPosGenerator = null
  override def create(): Unit = {
    Storage.assetManager = new AssetManager()

    MapEnum.values.foreach { e => Storage.assetManager.loadMapAsset(e.toString()) }

    Storage.createScreens()
    Storage.screens.get(MainScreen) match {
      case Some(x: GameScreen) =>
        Storage.currentScreen = x
        setScreen(x)
        Gdx.app.debug(TAG, "screen set")
        val locator = new RandomLocator(x.map)
        randomPosGenerator = new RandomPosGenerator(locator.UUID, Storage.locationsRef, x.map)
        Storage.camera.setLocationEntity(locator)
        Storage.locations.add(locator)
      case None =>
        Gdx.app.debug(TAG, "screen not set")
      case _ =>
    }

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
