package com.jellobird.testgame.storage

import com.badlogic.gdx.Screen
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.maps.LocationEntity
import com.jellobird.testgame.screen.{Camera, MainGameScreen}

/**
  * Created by jbc on 01.12.16.
  */
private[this] class Storage {

  val locations = new Repository[LocationEntity] {}

}

object Storage {

  private[this] val storage = new Storage

  def locations = storage.locations

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
  }

  // ************
  // Setup
  // ************
  def createScreens() = {
    import ScreenType._
    screens += ( MainScreen -> new MainGameScreen() )
  }

}
