package com.jellobird.testgame.visuals

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.visuals.Visual.SpriteMap

import scala.collection.mutable

/**
  * Created by jbc on 10.12.16.
  */
class Animation(spritemap: SpriteMap.Value) {

  val texture = Storage.assetManager.getTextureAsset(spritemap.toString)
  val tileWidth = texture.getWidth / 4
  val tileHeight = texture.getHeight / 4

  val textureRegion: Array[Array[g2d.TextureRegion]] = g2d.TextureRegion.split(texture, tileWidth, tileHeight)

  val animations = mutable.HashMap[Visual.State.Value, g2d.Animation]()
  val interval = 0.15f
  animations.put(Visual.State.N, new g2d.Animation(interval, textureRegion(3): _*))
  animations.put(Visual.State.S, new g2d.Animation(interval, textureRegion(0): _*))
  animations.put(Visual.State.E, new g2d.Animation(interval, textureRegion(2): _*))
  animations.put(Visual.State.NE, animations.get(Visual.State.E).get)
  animations.put(Visual.State.SE, animations.get(Visual.State.E).get)
  animations.put(Visual.State.W, new g2d.Animation(interval, textureRegion(1): _*))
  animations.put(Visual.State.NW, animations.get(Visual.State.W).get)
  animations.put(Visual.State.SW, animations.get(Visual.State.W).get)
  animations.put(Visual.State.HOLD, new g2d.Animation(interval, textureRegion(0)(0)))

  import Animation.stateTime
  def textureRegion(state: Visual.State.Value): g2d.TextureRegion = {
    stateTime += Gdx.graphics.getDeltaTime
    animations.get(state).get.getKeyFrame(stateTime, true)
  }

}

object Animation {

  var stateTime = 0f

}