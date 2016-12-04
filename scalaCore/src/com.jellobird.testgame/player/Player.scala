package com.jellobird.testgame.player

import akka.actor.Actor
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.ScalaBludBourne
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.InputKeyState
import com.jellobird.testgame.maps.LocationEntity
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.repositories.LocationsRepository.DeltaPos

/**
  * Created by jbc on 04.12.16.
  */
class Player(val locator: LocationEntity) extends Actor {

  val storage = Storage.locationsRef

  override def receive: Receive = {
    case KeyEvents(keyStates) => keyStates.foreach { keyState =>
      keyState match {
        case InputKeyState(Keys.UP, _) => storage ! new DeltaPos(new Vector2(0, 1), locator.UUID)
        case InputKeyState(Keys.DOWN, _) => storage ! new DeltaPos(new Vector2(0, -1), locator.UUID)
        case InputKeyState(Keys.RIGHT, _) => storage ! new DeltaPos(new Vector2(1, 0), locator.UUID)
        case InputKeyState(Keys.LEFT, _) => storage ! new DeltaPos(new Vector2(-1, 0), locator.UUID)
        case _ =>
      }
    }
  }
}
