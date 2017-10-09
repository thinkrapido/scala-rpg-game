package com.jellobird.testgame.visuals

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.MapPosition
import com.jellobird.testgame.screen.GameScreen

/**
  * Created by jbc on 10.12.16.
  */
class Visual(val animation: Animation) {

  import Visual._

  val uuid: UUID = UUID.randomUUID()

  var position: MapPosition = null

  var direction = State.HOLD

  val mapPosition = new Vector2()

  def render(batch: SpriteBatch): Unit = {
    if (position != null) {
      val tr = animation.textureRegion(direction)
      mapPosition.set(position.curr.position).scl(GameScreen.current.map.tilePixelWidth)
      batch.draw(tr, mapPosition.x, mapPosition.y)
    }
  }

}

object Visual {

  object SpriteMap extends Enumeration {
    val Warrior = Value("sprites/characters/Warrior.png")
  }

  object State extends Enumeration {
    val HOLD, N, S, E, W, NE, NW, SE, SW = Value
  }

}
