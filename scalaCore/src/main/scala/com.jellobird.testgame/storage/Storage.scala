package com.jellobird.testgame.storage

import akka.actor.ActorSystem
import com.badlogic.gdx.Screen
import com.badlogic.gdx.utils.viewport.{ScalingViewport, Viewport}
import com.jellobird.testgame.assets.AssetManager
import com.jellobird.testgame.maps.LocationsRegistry
import com.jellobird.testgame.screen.{Camera, MainGameScreen}
import com.jellobird.testgame.time.TickerRegistry
import com.jellobird.testgame.visuals.VisualsRegistry

object Storage {

  val actorSystem = ActorSystem("testgame")

  val locationsRegistry = actorSystem.actorOf(LocationsRegistry.props)

  val visualsRegistry = actorSystem.actorOf(VisualsRegistry.props)

  val tickerRegistry = actorSystem.actorOf(TickerRegistry.props)

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
