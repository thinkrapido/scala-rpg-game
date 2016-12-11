package com.jellobird.testgame.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Map
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.registry.LocationsRegistry.Process

trait GameScreen extends ScreenAdapter {

  private val TAG = classOf[GameScreen].getSimpleName

  protected var renderer: OrthogonalTiledMapRenderer = null
  val map: Map

  override def show(): Unit = super.show(); GameScreen.current = this

  override def render(elapsed: Float): Unit = {
    Storage.locations ! Process(null, "update", elapsed)
  }

}

object GameScreen {
  var current: GameScreen = null
}
