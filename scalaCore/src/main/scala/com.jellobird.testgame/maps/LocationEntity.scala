package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Entity

/**
  * Created by jbc on 01.12.16.
  */
trait LocationEntity extends Entity {

  import LocationEntity._
  import LocationEntity.CollisionMethodEnum._

  var _curr: Vector2
  var _next: Vector2

  def curr(): Vector2 = _curr
  
  def calcNextLocation(elapsed: Float): Unit = {
    _curr = _next
    calcNextLocationAlgorithm(elapsed)
  }
  
  protected def calcNextLocationAlgorithm(elapsed: Float) = {}
  
  def willCollideWith(that: Vector2, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = collisionMethod match {
    case Manhattan => that.x > _next.x - radius && that.x < _next.x + radius && that.y > _next.y - radius && that.x < _next.y + radius
    case Radius => that.dst(_next) < radius
  }

  def willCollideWith(that: LocationEntity, radius: Float)(implicit collisionMethod: CollisionMethodEnum.Value): Boolean = {
    willCollideWith(that._next, radius)(collisionMethod)
  }
  
}

object LocationEntity {

  implicit val collisionMethod = CollisionMethodEnum.Manhattan

  object CollisionMethodEnum extends Enumeration {
    val Manhattan, Radius = Value
  }
  
}
