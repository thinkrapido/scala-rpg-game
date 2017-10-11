package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.{Actor, PoisonPill}
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Storage
import PositionsRegistry._
import com.jellobird.testgame.input.InputObserver
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.time.TickerRegistry.{RegisterTickerObserver, UnRegisterTickerObserver}
import com.jellobird.testgame.utils.ScaleFactor
import com.jellobird.testgame.visuals.VisualsRegistry.UpdateVisual

/**
  * Created by jbc on 01.12.16.
  */
class Position extends Actor with MapPosition with Tick {

  var uuid: UUID = null
  var visual_uuid: UUID = null

  register

  override def receive: Receive = {
    case PositionCreated(uuid) => this.uuid = uuid
    case SetDestination(_, "next", vec: Vector2) => setDestination(vec)
    case SetDestination(_, "visual", uuid: UUID) =>
      visual_uuid = uuid
      Storage.visualsRegistry ! UpdateVisual(uuid, "position", this)
    case GetDestination(_, "curr") => sender ! SetDestination(null, "next", curr.position)
    case PoisonPill => destroy
  }

  def register = {
    if (uuid == null) {
      Storage.positionsRegistry ! RegisterPosition(self)
      Storage.tickerRegistry ! RegisterTickerObserver("input:keys:notify", this)
    }
  }

  def destroy = {
    if (uuid != null) {
      Storage.positionsRegistry ! DestroyPosition(uuid)
      Storage.tickerRegistry ! UnRegisterTickerObserver("input:keys:notify", this)
    }
  }

  override val step: Float = .5f
  override val scaleFactor: ScaleFactor = InputObserver.NOTIFY_INTERVALL

  override def penalty: Float = 1f
}
