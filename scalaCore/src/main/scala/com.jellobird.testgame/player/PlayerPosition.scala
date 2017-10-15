package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Position
import com.jellobird.testgame.maps.PositionsRegistry.SetDestination
import com.jellobird.testgame.utils.world.{Area, Range}

/**
  * Created by jbc on 03.12.16.
  */
class PlayerPosition(val startPosition: Vector2, tile: Range) extends Position {

  _last = Area(startPosition, tile)
  _next = _last
  _helper = _last

  override def penalty: Float = 1f

  override def receive: Receive = super.receive orElse {
    case SetDestination(_, "oneStepFurther", payload: Vector2) => nextMapPosition(payload)
  }

}
