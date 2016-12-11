package com.jellobird.testgame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import com.jellobird.testgame.maps.TopWorldMap
import com.jellobird.testgame.storage.Storage

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import akka.pattern.ask
import akka.util.Timeout
import com.badlogic.gdx.utils.viewport.{FillViewport, StretchViewport, Viewport}
import com.jellobird.testgame.visuals.VisualsRegistry.Render

/**
  * Created by jbc on 27.11.16.
  */
class MainGameScreen extends GameScreen {

  implicit val ec = ExecutionContext.Implicits.global
  implicit val timeout = Timeout(100.milliseconds)

  private val TAG = classOf[MainGameScreen].getSimpleName

  override val map = new TopWorldMap()

  var batch: SpriteBatch = null

  private val tilesVisible = 20

  override def show(): Unit = {
    super.show()

    if( renderer == null ) {
      renderer = new OrthogonalTiledMapRenderer(map.tiledMap);
    }
    Storage.viewport = new FillViewport(map.tilePixelWidth * tilesVisible,map.tilePixelHeight * tilesVisible,Storage.camera)
    Storage.viewport.apply()
    batch = new SpriteBatch()
  }

  override def render(elapsed: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    super.render(elapsed)

    Storage.camera.update()
    batch.setProjectionMatrix(Storage.camera.combined)
    renderer.setView(Storage.camera)

    renderer.render()

    batch.begin()
    val f = ask(Storage.visualsRegistry, Render(batch))
    Await.result(f, 200.milliseconds)
    batch.end()

  }

  override def resize(width: Int, height: Int): Unit = {
    Storage.viewport.update(width, height)
  }

  override def dispose(): Unit = {

    Array[Disposable](
      renderer
    ) foreach { disposable =>
      if (disposable != null) disposable.dispose()
    }

  }

}
