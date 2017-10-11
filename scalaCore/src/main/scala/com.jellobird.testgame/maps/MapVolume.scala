package com.jellobird.testgame.maps

/**
  * Created by jbc on 23.12.16.
  */
trait MapVolume {

  this: MapPosition =>

}

object MapVolume {

  implicit val collisionMethod = CollisionMethodEnum.Manhattan

  object CollisionMethodEnum extends Enumeration {
    val Manhattan, Radius = Value
  }

}
