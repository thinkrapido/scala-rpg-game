package com.jellobird.testgame.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.jellobird.testgame.maps.{LocationEntity, Map, RandomLocator}
import com.jellobird.testgame.storage.Storage

trait GameScreen extends ScreenAdapter {

  private val TAG = classOf[GameScreen].getSimpleName

  protected var renderer: OrthogonalTiledMapRenderer = null
  private var locator: LocationEntity = null
  val map: Map

  override def render(elapsed: Float): Unit = {
    Storage.locations.all().foreach { _.calcNextLocation(elapsed) }
  }

  def viewportDimension = VIEWPORT.viewport

  protected object VIEWPORT {
    var viewport = new Dimension()
    var virtual = new Dimension()
    val tilesVisible = 15

    class Dimension(var width: Float = 0, var height: Float = 0) {
      def this(map: Map) = this(map.tilePixelWidth, map.tilePixelHeight)
      def ratio: Float = width / height
      def copy = new Dimension(width, height)
      def scale(factor: Float) = new Dimension(width * factor, height * factor)
      override def toString(): String = "Dimension: (w: %f,h: %f, r: %f)".format(width, height, ratio)
    }

    def setupViewPort(width: Int, height: Int): Unit = {

      virtual = new Dimension(width, height)

      viewport = new Dimension(tilesVisible, tilesVisible)

      if (virtual.ratio > 1) {
        viewport.width *= virtual.ratio
      }
      else {
        viewport.height /= virtual.ratio
      }

    }

  }

}
