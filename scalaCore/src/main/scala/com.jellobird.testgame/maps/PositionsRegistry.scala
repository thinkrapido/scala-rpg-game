package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.jellobird.testgame.storage.Storage

import scala.collection.mutable

/**
  * Created by jbc on 09.12.16.
  */
class PositionsRegistry extends Actor {

  import PositionsRegistry._

  val hash = new mutable.HashMap[UUID, ActorRef]()

  override def receive: Receive = {
    case CreatePosition =>
      val uuid: UUID = UUID.randomUUID()
      val actorRef = Storage.actorSystem.actorOf(Props[Position])
      hash.put(uuid, actorRef)
      sender ! PositionCreated(uuid)
    case RegisterPosition(actorRef) =>
      val uuid: UUID = UUID.randomUUID()
      hash.put(uuid, actorRef)
      sender ! PositionCreated(uuid)
    case DestroyPosition(uuid) =>
      hash.remove(uuid) match {
        case Some(actorRef) =>
          actorRef ! PoisonPill
          sender ! PositionDestroyed(uuid) // TODO: never used
        case _ =>
      }
    case x @ SetDestination(uuid, _, _) =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          actorRef ! x
        case _ =>
      }
    case x @ GetDestination(uuid, "position") =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          sender ! SetDestination(null, "position", actorRef)
        case _ =>
      }
    case x @ GetDestination(uuid, _) => // TODO: something is wrong with this
      hash.get(uuid) match {
        case Some(actorRef) =>
          actorRef ! x // reason about this. must be sender forward x or something
        case _ =>
      }
    case x @ Process(_, _, _) =>
      hash.values.foreach(_ ! x)
  }
}

object PositionsRegistry {

  def props = Props[PositionsRegistry]

  sealed abstract trait PositionCrud
  case object CreatePosition extends PositionCrud
  case class RegisterPosition(actorRef: ActorRef) extends PositionCrud
  case class DestroyPosition(uuid: UUID) extends PositionCrud
  case class SetDestination(uuid: UUID, what: Any, payload: Any) extends PositionCrud
  case class GetDestination(uuid: UUID, what: Any) extends PositionCrud

  sealed abstract trait PositionLifetime
  case class PositionCreated(uuid: UUID) extends PositionLifetime
  case class PositionRegistered(uuid: UUID) extends PositionLifetime
  case class PositionDestroyed(uuid: UUID) extends PositionLifetime
  case class Process(uuid: UUID, what: Any, payload: Any) extends PositionLifetime
}