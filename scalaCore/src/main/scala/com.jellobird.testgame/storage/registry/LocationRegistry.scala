package com.jellobird.testgame.storage.registry

import java.util.UUID

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.jellobird.testgame.maps.Location
import com.jellobird.testgame.storage.Storage

import scala.collection.mutable
import scala.reflect._

/**
  * Created by jbc on 09.12.16.
  */
class LocationRegistry extends Actor {

  import LocationRegistry._

  val hash = new mutable.HashMap[UUID, ActorRef]()

  override def receive: Receive = {
    case Create =>
      val uuid: UUID = UUID.randomUUID()
      val actorRef = Storage.actorSystem.actorOf(Props[Location])
      hash.put(uuid, actorRef)
      sender ! Created(uuid)
    case Register(actorRef) =>
      val uuid: UUID = UUID.randomUUID()
      hash.put(uuid, actorRef)
      sender ! Created(uuid)
    case Destroy(uuid) =>
      hash.remove(uuid) match {
        case Some(actorRef) =>
          actorRef ! PoisonPill
          sender ! Destroyed(uuid)
        case _ =>
      }
    case x @ Set(uuid, _, _) =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          actorRef ! x
        case _ =>
      }
    case x @ Get(uuid, "location") =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          sender ! Set(_, "location", actorRef)
        case _ =>
      }
    case x @ Get(uuid, _) =>
      hash.get(uuid) match {
        case Some(actorRef) =>
          actorRef ! x
        case _ =>
      }
    case x @ Process(_, _, _) =>
      hash.values.foreach(_ ! x)
  }
}

object LocationRegistry {

  sealed abstract trait Crud
  case object Create extends Crud
  case class Register(actorRef: ActorRef) extends Crud
  case class Destroy(uuid: UUID) extends Crud
  case class Set(uuid: UUID, what: Any, payload: Any) extends Crud
  case class Get(uuid: UUID, what: Any) extends Crud

  sealed abstract trait Lifetime
  case class Created(uuid: UUID) extends Lifetime
  case class Registered(uuid: UUID) extends Lifetime
  case class Destroyed(uuid: UUID) extends Lifetime
  case class Process(uuid: UUID, what: Any, payload: Any) extends Lifetime
}