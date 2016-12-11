package com.jellobird.testgame.visuals

import java.util.UUID

import akka.actor.Actor
import akka.util.Timeout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.visuals.Visual.SpriteMap

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by jbc on 09.12.16.
  */
class VisualsRegistry extends Actor {

  import VisualsRegistry._

  val hash = new mutable.HashMap[UUID, Visual]()
  // for lightweight pattern:
  val poolOfAnimations = new mutable.HashMap[SpriteMap.Value, Animation]()

  override def receive: Receive = {
    case CreateVisual(spriteMap) =>
      var animation: Animation = null
      poolOfAnimations.get(spriteMap) match {
        case Some(a) => animation = a
        case _ => animation = new Animation(spriteMap); poolOfAnimations.put(spriteMap, animation)
      }
      val visual = new Visual(animation)
      hash.put(visual.uuid, visual)
      sender ! VisualCreated(visual.uuid)
    case DestroyVisual(uuid) =>
      hash.remove(uuid) match {
        case Some(_) =>
          sender ! VisualDestroyed(uuid)
        case _ =>
      }
    case UpdateVisual(uuid, "position", position: Vector2) =>
      hash.get(uuid) match {
        case Some(visual) => visual.position = position
        case _ =>
      }
    case UpdateVisual(uuid, "state", newState: Visual.State.Value) =>
      hash.get(uuid) match {
        case Some(visual) => visual state = newState
        case _ =>
      }
    case GetVisual(uuid, _) =>
      hash.get(uuid) match {
        case Some(visual) =>
          sender ! visual
        case _ =>
      }
    case Render(batch) =>
      hash.values.foreach(_.render(batch))
      sender ! batch
  }
}

object VisualsRegistry {

  sealed abstract trait VisualCrud
  case class CreateVisual(payload: SpriteMap.Value) extends VisualCrud
  case class DestroyVisual(uuid: UUID) extends VisualCrud
  case class UpdateVisual(uuid: UUID, what: Any, payload: Any) extends VisualCrud // TODO: name it set or update?
  case class GetVisual(uuid: UUID, what: Any) extends VisualCrud

  sealed abstract trait VisualLifetime
  case class VisualCreated(uuid: UUID) extends VisualLifetime
  case class VisualDestroyed(uuid: UUID) extends VisualLifetime
  case class Render(batch: SpriteBatch) extends VisualLifetime
}