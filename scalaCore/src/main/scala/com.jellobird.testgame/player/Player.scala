package com.jellobird.testgame.player

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.{InputEvents, InputKeyState}
import com.jellobird.testgame.input.InputKeyState._
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.maps.PositionsRegistry.SetDestination
import com.jellobird.testgame.utils.DelayFunction
import com.jellobird.testgame.visuals.Visual
import com.jellobird.testgame.visuals.Visual.SpriteMap
import com.jellobird.testgame.visuals.VisualsRegistry.{CreateVisual, UpdateVisual, VisualCreated}

/**
  * Created by jbc on 04.12.16.
  */
class Player(val position: ActorRef) extends Actor {

  import scala.concurrent.duration._

  private var visual_uuid: UUID = null

  Storage.visualsRegistry ! CreateVisual(SpriteMap.Warrior)

  val delayHoldVisual = new DelayFunction(() => {
    Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.HOLD)
  }, 150.milliseconds)

  override def receive: Receive = {
    case k @ KeyEvents(ks) =>

      if (k.direction != Visual.State.HOLD) {
        position ! SetDestination(null, "oneStepFurther", InputEvents.vector(k.direction))
        Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", k.direction)
        delayHoldVisual.renew
      }

    case VisualCreated(uuid) =>
      visual_uuid = uuid
      position ! SetDestination(null, "visual", uuid)
  }
}
