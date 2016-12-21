package com.jellobird.testgame.visuals

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.screen.GameScreen

/**
  * Created by jbc on 10.12.16.
  */
class Visual(val animation: Animation) {

  import Visual._

  val uuid: UUID = UUID.randomUUID()

  var position: Vector2 = null

  var _direction = State.HOLD

  def direction = _direction
  def direction_=(newDirection: State.Value): Unit = {
    if (_direction != newDirection) newDirection match {
      case State.N => _direction match {
        case State.E => _direction = State.NE
        case State.W => _direction = State.NW
        case _ => _direction = State.N
      }
      case State.S => _direction match {
        case State.E => _direction = State.SE
        case State.W => _direction = State.SW
        case _ => _direction = State.S
      }
      case State.E =>_direction match {
        case State.N => _direction = State.NE
        case State.S => _direction = State.SE
        case _ => _direction = State.E
      }
      case State.W =>_direction match {
        case State.N => _direction = State.NW
        case State.S => _direction = State.SW
        case _ => _direction = State.W
      }
      case _ => _direction = State.HOLD
    }
  }

  def render(batch: SpriteBatch): Unit = {
    if (position != null) {
      val tr = animation.textureRegion(_direction)
      val mapPosition = new Vector2(position).scl(GameScreen.current.map.tilePixelWidth)
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
