package com.jellobird.testgame.storage

import akka.actor.{ActorSystem, Props}
import com.badlogic.gdx.Screen
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.screen.{Camera, MainGameScreen}
import com.jellobird.testgame.storage.repositories.{LocationsRepository, LocationsRepositoryActor}

/**
  * Created by jbc on 01.12.16.
  */
private[this] class Storage {

  import Storage._

  var locations: LocationsRepository = new LocationsRepository
  val locationsRef = actorSystem.actorOf(Props(new LocationsRepositoryActor(locations)), "locations")

}

object Storage {

  val actorSystem = ActorSystem("testgame")

  private[this] val storage = new Storage

  def locations = storage.locations
  def locationsRef = storage.locationsRef

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
