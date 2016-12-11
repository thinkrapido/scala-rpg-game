package com.jellobird.testgame.storage

import akka.actor.{ActorSystem, Props}
import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.viewport.{ScalingViewport, Viewport}
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.screen.{Camera, MainGameScreen}
import com.jellobird.testgame.storage.registry.LocationsRegistry
import com.jellobird.testgame.visuals.VisualsRegistry

/**
  * Created by jbc on 01.12.16.
  */
private[this] class Storage {

  var locations = Storage.actorSystem.actorOf(Props[LocationsRegistry])

}

object Storage {

  val actorSystem = ActorSystem("testgame")

  private[this] val storage = new Storage

  val locations = storage.locations

  val visualsRegistry = actorSystem.actorOf(Props[VisualsRegistry])

  var currentScreen: Screen = null
  var assetManager: AssetManager = null

  val camera = new Camera
  var viewport: ScalingViewport = null
  var screens = Map[ ScreenType.Value, Screen ]()

  object ScreenType extends Enumeration {
    val MainScreen = Value
  }

  def dispose() = {
    screens.foreach( _._2.dispose() )
    assetManager = null
    actorSystem.terminate()
  }

  // ************
  // Setup
  // ************
  def createScreens() = {
    import ScreenType._
    screens += ( MainScreen -> new MainGameScreen() )
  }

}
