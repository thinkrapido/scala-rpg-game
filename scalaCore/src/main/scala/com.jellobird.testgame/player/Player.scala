package com.jellobird.testgame.player

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.InputKeyState
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.registry.LocationsRegistry.SetLocation
import com.jellobird.testgame.utils.DelayFunction
import com.jellobird.testgame.visuals.Visual
import com.jellobird.testgame.visuals.Visual.SpriteMap
import com.jellobird.testgame.visuals.VisualsRegistry.{CreateVisual, UpdateVisual, VisualCreated}

/**
  * Created by jbc on 04.12.16.
  */
class Player(val locator: ActorRef) extends Actor {

  import scala.concurrent.duration._

  private var visual_uuid: UUID = null

  Storage.visualsRegistry ! CreateVisual(SpriteMap.Warrior)

  val delayHoldVisual = new DelayFunction(() => {
    Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.HOLD)
  }, 150.milliseconds)

  override def receive: Receive = {
    case KeyEvents(keyStates) => keyStates.foreach { keyState =>
      keyState match {
        case InputKeyState(Keys.UP) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(0, 1))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.N)
          delayHoldVisual.renew
        case InputKeyState(Keys.DOWN) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(0, -1))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.S)
          delayHoldVisual.renew
        case InputKeyState(Keys.RIGHT) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(1, 0))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.E)
          delayHoldVisual.renew
        case InputKeyState(Keys.LEFT) =>
          locator ! SetLocation(null, "deltaNext", new Vector2(-1, 0))
          Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.W)
          delayHoldVisual.renew
        case _ =>
      }

    }
    case VisualCreated(uuid) =>
      visual_uuid = uuid
      locator ! SetLocation(null, "visual", uuid)
  }
}
