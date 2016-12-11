package com.jellobird.testgame.visuals

import java.util.UUID

import akka.actor.Cancellable
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.{Vector2, Vector3}
import com.badlogic.gdx.utils.TimeUtils
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.storage.Storage

import scala.concurrent.ExecutionContext


/**
  * Created by jbc on 10.12.16.
  */
class Visual(val animation: Animation) {

  import Visual._
  import scala.concurrent.duration._
  implicit val ec = ExecutionContext.Implicits.global

  val uuid: UUID = UUID.randomUUID()

  var position: Vector2 = null

  var _state = State.HOLD
  var _lastTimeTicked = TimeUtils.millis()

  var counter: Byte = 0

  implicit def int2Byte(n: Int) = n.toByte

  var timer: Cancellable = null
  def startTimer = {
    if (timer != null && timer.isCancelled) {
      counter = 0
      timer = Storage.actorSystem.scheduler.schedule(150.milliseconds, 750.milliseconds) {
        counter = counter + 1
      }
    }
  }
  def cancelTimer = if (timer != null && !timer.isCancelled) timer.cancel()

  def state = _state
  def state_=(newState: State.Value): Unit = {
    val delta = TimeUtils.millis() - _lastTimeTicked

    if (delta > 200) {
      _state = State.HOLD
    }
    else {
      newState match {
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
        case _ => _state = State.HOLD; cancelTimer
      }
    }

    if (_state != State.HOLD) startTimer

    _lastTimeTicked = TimeUtils.millis()
  }

  def render(batch: SpriteBatch): Unit = {
    if (position != null) {
      val tr = animation.textureRegion(_state, counter)
      val mapPosition = Storage.camera.project(new Vector3(position.cpy(),0))
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
