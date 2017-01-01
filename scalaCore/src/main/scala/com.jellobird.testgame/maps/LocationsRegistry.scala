package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.jellobird.testgame.storage.Storage

import scala.collection.mutable

/**
  * Created by jbc on 09.12.16.
  */
class LocationsRegistry extends Actor {

  import LocationsRegistry._

  val hash = new mutable.HashMap[UUID, ActorRef]()

  override def receive: Receive = {
    case CreateLocation =>
      val uuid: UUID = UUID.randomUUID()
      val actorRef = Storage.actorSystem.actorOf(Props[Location])
      hash.put(uuid, actorRef)
      sender ! LocationCreated(uuid)
    case RegisterLocation(actorRef) =>
      val uuid: UUID = UUID.randomUUID()
      hash.put(uuid, actorRef)
      sender ! LocationCreated(uuid)
    case DestroyLocation(uuid) =>
      hash.remove(uuid) match {
        case Some(actorRef) =>
          actorRef ! PoisonPill
          sender ! LocationDestroyed(uuid) // TODO: never used
        case _ =>
      }
    case x @ SetDestination(uuid, _, _) =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          actorRef ! x
        case _ =>
      }
    case x @ GetDestination(uuid, "location") =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          sender ! SetDestination(null, "location", actorRef)
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

object LocationsRegistry {

  def props = Props[LocationsRegistry]

  sealed abstract trait LocationCrud
  case object CreateLocation extends LocationCrud
  case class RegisterLocation(actorRef: ActorRef) extends LocationCrud
  case class DestroyLocation(uuid: UUID) extends LocationCrud
  case class SetDestination(uuid: UUID, what: Any, payload: Any) extends LocationCrud
  case class GetDestination(uuid: UUID, what: Any) extends LocationCrud

  sealed abstract trait LocationLifetime
  case class LocationCreated(uuid: UUID) extends LocationLifetime
  case class LocationRegistered(uuid: UUID) extends LocationLifetime
  case class LocationDestroyed(uuid: UUID) extends LocationLifetime
  case class Process(uuid: UUID, what: Any, payload: Any) extends LocationLifetime
}