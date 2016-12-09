package com.jellobird.testgame.player

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.maps.Location

/**
  * Created by jbc on 03.12.16.
  */
class PlayerLocation(val startPosition: Vector2) extends Location {
  _curr = startPosition
  _next = _curr
}