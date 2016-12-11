package com.jellobird.testgame.maps

import java.util.UUID

import akka.actor.{Actor, PoisonPill}
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Storage
import com.jellobird.testgame.storage.registry.LocationsRegistry._
import com.jellobird.testgame.visuals.VisualsRegistry.UpdateVisual

/**
  * Created by jbc on 01.12.16.
  */
class Location extends Actor {

  import Location._
  import Location.CollisionMethodEnum._

  var uuid: UUID = null
  var visual_uuid: UUID = null

  var _curr: Vector2 = null
  var _next: Vector2 = null

  register

  def update(elapsed: Float): Unit = {
    calcNextLocationAlgorithm(elapsed)
  }

  protected def calcNextLocationAlgorithm(elapsed: Float) = {
    if (_next != null) _curr = _next; Storage.visualsRegistry ! UpdateVisual(visual_uuid, "position", _curr)
  }

  def willCollideWith(that: Vector2, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = collisionMethod match {
    case Manhattan => that.x > _next.x - radius && that.x < _next.x + radius && that.y > _next.y - radius && that.x < _next.y + radius
    case Radius => that.dst(_next) < radius
  }

  def willCollideWith(that: Location, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = {
    willCollideWith(that._next, radius)(collisionMethod)
  }

  override def receive: Receive = {
    case SetLocation(_, "next", vec: Vector2) => _next = vec
    case SetLocation(_, "visual", uuid: UUID) => visual_uuid = uuid
    case GetLocation(_, "curr") => sender ! SetLocation(null, "next", _curr)
    case Process(_, "update", elapsed: Float) => update(elapsed)
    case PoisonPill => destroy
  }

  def register = {
    if (uuid == null) Storage.locations ! RegisterLocation(self)
  }

  def destroy = {
    if (uuid != null) Storage.locations ! DestroyLocation(uuid)
  }
}

object Location {

  implicit val collisionMethod = CollisionMethodEnum.Manhattan

  object CollisionMethodEnum extends Enumeration {
    val Manhattan, Radius = Value
  }
  
}
