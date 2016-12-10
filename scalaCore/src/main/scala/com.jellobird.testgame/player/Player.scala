package com.jellobird.testgame.player

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.InputKeyState
import com.jellobird.testgame.storage.registry.LocationRegistry.SetLocation

/**
  * Created by jbc on 04.12.16.
  */
class Player(val locator: ActorRef) extends Actor {

  override def receive: Receive = {
    case KeyEvents(keyStates) => keyStates.foreach { keyState =>
      keyState match {
        case InputKeyState(Keys.UP, _) => locator ! SetLocation(null, "deltaNext", new Vector2(0, 1))
        case InputKeyState(Keys.DOWN, _) => locator ! SetLocation(null, "deltaNext", new Vector2(0, -1))
        case InputKeyState(Keys.RIGHT, _) => locator ! SetLocation(null, "deltaNext", new Vector2(1, 0))
        case InputKeyState(Keys.LEFT, _) => locator ! SetLocation(null, "deltaNext", new Vector2(-1, 0))
        case _ =>
      }
    }
  }
}
