package com.jellobird.testgame.maps

/**
  * Created by jbc on 23.12.16.
  */
trait MapVolume {

  this: MapPosition =>

/*
  def collidesWith(that: MapPosition): Boolean = {

  }

  def collidesWith(that: Vector2, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = collisionMethod match {
    case Manhattan => that.x > _next.x - radius && that.x < _next.x + radius && that.y > _next.y - radius && that.x < _next.y + radius
    case Radius => that.dst(_next) < radius
  }

  def collidesWith(that: Location, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = {
    willCollideWith(that._next, radius)(collisionMethod)
  }
*/

}

object MapVolume {

  implicit val collisionMethod = CollisionMethodEnum.Manhattan

  object CollisionMethodEnum extends Enumeration {
    val Manhattan, Radius = Value
  }

}
