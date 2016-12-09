package com.jellobird.testgame.storage

import akka.actor.{ActorSystem, Props}
import com.badlogic.gdx.Screen
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.screen.{Camera, MainGameScreen}
import com.jellobird.testgame.storage.registry.LocationRegistry

/**
  * Created by jbc on 01.12.16.
  */
private[this] class Storage {

  var locations = Storage.actorSystem.actorOf(Props[LocationRegistry])

}

object Storage {

  val actorSystem = ActorSystem("testgame")

  private[this] val storage = new Storage

  val locations = storage.locations

  var currentScreen: Screen = null
  var assetManager: AssetManager = null

  val camera = new Camera
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
