package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.{BoundingBox, Location, Tile}
import com.jellobird.testgame.maps.LocationsRegistry.SetDestination

/**
  * Created by jbc on 03.12.16.
  */
class PlayerLocation(val startPosition: Vector2, tile: Tile) extends Location {

  override val _last: BoundingBox = BoundingBox.build.move(startPosition).tile(tile).get
  override val _next: BoundingBox = _last
  override val _helper: BoundingBox = _last

  override def penalty: Float = 1f

  override def receive: Receive = super.receive orElse {
    case SetDestination(_, "oneStepFurther", payload: Vector2) => nextMapPosition(payload)
  }

}
