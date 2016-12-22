package com.jellobird.testgame.player

import java.util.UUID

import akka.actor.{Actor, ActorRef}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.input.InputEvents.KeyEvents
import com.jellobird.testgame.input.InputKeyState
import com.jellobird.testgame.input.InputKeyState._
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

  private var speedFactor = 3f

  Storage.visualsRegistry ! CreateVisual(SpriteMap.Warrior)

  val delayHoldVisual = new DelayFunction(() => {
    Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", Visual.State.HOLD)
  }, 150.milliseconds)

  override def receive: Receive = {
    case KeyEvents(ks) =>

      val direction =
        if (Set[InputKeyState](Keys.UP, Keys.LEFT) forall (ks contains)) Some(Visual.State.NW)
        else if (Set[InputKeyState](Keys.UP, Keys.RIGHT) forall (ks contains)) Some(Visual.State.NE)
        else if (Set[InputKeyState](Keys.DOWN, Keys.LEFT) forall (ks contains)) Some(Visual.State.SW)
        else if (Set[InputKeyState](Keys.DOWN, Keys.RIGHT) forall (ks contains)) Some(Visual.State.SE)
        else if (ks contains Keys.UP) Some(Visual.State.N)
        else if (ks contains Keys.DOWN) Some(Visual.State.S)
        else if (ks contains Keys.LEFT) Some(Visual.State.W)
        else if (ks contains Keys.RIGHT) Some(Visual.State.E)
        else None

      if (direction != None) {
        locator ! SetLocation(null, "deltaNext", direction match {
          case Some(Visual.State.N)  => new Vector2( 0              ,  1 * speedFactor)
          case Some(Visual.State.S)  => new Vector2( 0              , -1 * speedFactor)
          case Some(Visual.State.W)  => new Vector2(-1 * speedFactor,  0)
          case Some(Visual.State.E)  => new Vector2( 1 * speedFactor,  0)
          case Some(Visual.State.NW) => new Vector2(-1 * speedFactor,  1 * speedFactor)
          case Some(Visual.State.NE) => new Vector2( 1 * speedFactor,  1 * speedFactor)
          case Some(Visual.State.SW) => new Vector2(-1 * speedFactor, -1 * speedFactor)
          case Some(Visual.State.SE) => new Vector2( 1 * speedFactor, -1 * speedFactor)
          case _ =>
        })
        Storage.visualsRegistry ! UpdateVisual(visual_uuid, "state", direction.getOrElse(Visual.State.HOLD))
        delayHoldVisual.renew
      }

    case VisualCreated(uuid) =>
      visual_uuid = uuid
      locator ! SetLocation(null, "visual", uuid)
  }
}
