package com.jellobird.testgame.visuals

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.jellobird.testgame.screen.GameScreen

/**
  * Created by jbc on 10.12.16.
  */
class Visual(val animation: Animation) {

  import Visual._

  val uuid: UUID = UUID.randomUUID()

  var position: Vector2 = null

  var _state = State.HOLD

  def state = _state
  def state_=(newState: State.Value): Unit = {
    if (_state != newState) newState match {
      case State.N => _state match {
        case State.E => _state = State.NE
        case State.W => _state = State.NW
        case _ => _state = State.N
      }
      case State.S => _state match {
        case State.E => _state = State.SE
        case State.W => _state = State.SW
        case _ => _state = State.S
      }
      case State.E =>_state match {
        case State.N => _state = State.NE
        case State.S => _state = State.SE
        case _ => _state = State.E
      }
      case State.W =>_state match {
        case State.N => _state = State.NW
        case State.S => _state = State.SW
        case _ => _state = State.W
      }
      case _ => _state = State.HOLD
    }
  }

  def render(batch: SpriteBatch): Unit = {
    if (position != null) {
      val tr = animation.textureRegion(_state)
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
