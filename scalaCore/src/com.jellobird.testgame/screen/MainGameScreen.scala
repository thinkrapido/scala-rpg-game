package com.jellobird.testgame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import com.jellobird.testgame.ScalaBludBourne
import com.jellobird.testgame.maps.TopWorldMap
import com.jellobird.testgame.storage.Storage

/**
  * Created by jbc on 27.11.16.
  */
class MainGameScreen extends GameScreen {

  import VIEWPORT._

  private val TAG = classOf[MainGameScreen].getSimpleName

  override val map = new TopWorldMap()

  override def show(): Unit = {
    if( renderer == null ){
      println(map.pixelWidth)
      println(map.width)
      println(map.tilePixelWidth)
      renderer = new OrthogonalTiledMapRenderer(map.tiledMap, 1/map.tilePixelWidth);
    }
    Gdx.input.setInputProcessor(ScalaBludBourne.inputObserver)
    Storage.camera.setToOrtho(false, viewport.width, viewport.height);
  }

  override def render(elapsed: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render(elapsed)

    Storage.camera.update()
    renderer.setView(Storage.camera)
    renderer.render()
  }

  override def resize(width: Int, height: Int): Unit = {
    setupViewPort(width,height)
    Storage.camera.setToOrtho(false, viewport.width, viewport.height);
  }

  override def dispose(): Unit = {

    Array[Disposable](
      renderer
    ) foreach { disposable =>
      if (disposable != null) disposable.dispose()
    }

  }

}
