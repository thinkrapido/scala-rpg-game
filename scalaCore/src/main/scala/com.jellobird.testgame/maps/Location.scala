package com.jellobird.testgame.maps

import akka.actor.Actor
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.registry.LocationRegistry.{Get, Process, Set}

/**
  * Created by jbc on 01.12.16.
  */
class Location extends Actor {

  import Location._
  import Location.CollisionMethodEnum._

  var _curr: Vector2 = null
  var _next: Vector2 = null

  def update(elapsed: Float): Unit = {
    calcNextLocationAlgorithm(elapsed)
  }

  protected def calcNextLocationAlgorithm(elapsed: Float) = {
    if (_next != null) _curr = _next
  }

  def willCollideWith(that: Vector2, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = collisionMethod match {
    case Manhattan => that.x > _next.x - radius && that.x < _next.x + radius && that.y > _next.y - radius && that.x < _next.y + radius
    case Radius => that.dst(_next) < radius
  }

  def willCollideWith(that: Location, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = {
    willCollideWith(that._next, radius)(collisionMethod)
  }

  override def receive: Receive = {
    case Set(_, "next", vec: Vector2) => _next = vec
    case Get(_, "curr") => sender ! Set(null, "next", _curr)
    case Process(_, "update", elapsed: Float) => update(elapsed)
  }
}

object Location {

  implicit val collisionMethod = CollisionMethodEnum.Manhattan

  object CollisionMethodEnum extends Enumeration {
    val Manhattan, Radius = Value
  }
  
}
