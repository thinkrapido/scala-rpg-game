package com.jellobird.testgame.input

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.visuals.Visual

/**
  * Created by jbc on 03.12.16.
  */
object InputEvents {

  case class KeyEvents(keyStates: Set[InputKeyState]) {
    private val _direction: Array[Array[Visual.State.Value]] = Array(
      Array( Visual.State.NW, Visual.State.N,    Visual.State.NE ),
      Array( Visual.State.W,  Visual.State.HOLD, Visual.State.E ),
      Array( Visual.State.SW, Visual.State.S,    Visual.State.SE )
    )
    def direction: Visual.State.Value = {
      var x = 1
      var y = 1
      if (keyStates contains Keys.LEFT)   x -= 1
      if (keyStates contains Keys.RIGHT)  x += 1
      if (keyStates contains Keys.UP)     y -= 1
      if (keyStates contains Keys.DOWN)   y += 1
      _direction(y)(x)
    }

  }

  def vector(direction: Visual.State.Value): Vector2 = direction match {
    case Visual.State.N  => new Vector2( 0,  1)
    case Visual.State.S  => new Vector2( 0, -1)
    case Visual.State.W  => new Vector2(-1,  0)
    case Visual.State.E  => new Vector2( 1,  0)
    case Visual.State.NW => new Vector2(-1,  1)
    case Visual.State.NE => new Vector2( 1,  1)
    case Visual.State.SW => new Vector2(-1, -1)
    case Visual.State.SE => new Vector2( 1, -1)
    case Visual.State.HOLD => new Vector2(0, 0)
  }

}
