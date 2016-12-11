package com.jellobird.testgame.visuals

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.visuals.Visual.SpriteMap

/**
  * Created by jbc on 10.12.16.
  */
class Animation(spritemap: SpriteMap.Value) {

  val texture = Storage.assetManager.getTextureAsset(spritemap.toString)
  val tileWidth = texture.getWidth / 4
  val tileHeight = texture.getHeight / 4

  val textureRegion: Array[Array[TextureRegion]] = TextureRegion.split(texture, tileWidth, tileHeight)

  val up = Set(Visual.State.N)
  val down = Set(Visual.State.S)
  val left = Set(Visual.State.W, Visual.State.NW, Visual.State.SW)
  val right = Set(Visual.State.E, Visual.State.NE, Visual.State.SE)

  def textureRegion(state: Visual.State.Value, counter: Byte): TextureRegion = {
    if (up contains state) textureRegion(3)(counter % textureRegion(3).size)
    else if (down contains state) textureRegion(0)(counter % textureRegion(0).size)
    else if (left contains state) textureRegion(1)(counter % textureRegion(1).size)
    else if (right contains state) textureRegion(2)(counter % textureRegion(2).size)
    else textureRegion(0)(0)
  }

}
