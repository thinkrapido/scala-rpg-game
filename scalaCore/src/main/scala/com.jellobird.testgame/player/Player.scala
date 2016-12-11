package com.jellobird.testgame.player

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.InputKeyState
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.registry.LocationsRegistry.SetLocation
import com.jellobird.testgame.visuals.Visual
import com.jellobird.testgame.visuals.Visual.SpriteMap
import com.jellobird.testgame.visuals.VisualsRegistry.{CreateVisual, UpdateVisual, VisualCreated}

/**
  * Created by jbc on 04.12.16.
  */
class Player(val locator: ActorRef) extends Actor {

  private var visual_uuid: UUID = null

  Storage.visualsRegistry ! CreateVisual(SpriteMap.Warrior)

  override def receive: Receive = {
    case KeyEvents(keyStates) => keyStates.foreach { keyState =>
      keyState match {
        case InputKeyState(Keys.UP, _) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(0, 1))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.N)
        case InputKeyState(Keys.DOWN, _) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(0, -1))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.S)
        case InputKeyState(Keys.RIGHT, _) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(1, 0))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.E)
        case InputKeyState(Keys.LEFT, _) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(-1, 0))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.W)
        case _ =>
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.HOLD)
      }
    }
    case VisualCreated(uuid) =>
      visual_uuid = uuid
      locator ! SetLocation(null, "visual", uuid)
  }
}
