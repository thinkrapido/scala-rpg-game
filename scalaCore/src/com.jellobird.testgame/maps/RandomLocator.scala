package com.jellobird.testgame.maps
import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.Payload

/**
  * Created by jbc on 01.12.16.
  */
class RandomLocator(private val _map: Map) extends LocationEntity {

  override protected var _curr: Vector2 = _
  override protected var _next: Vector2 = _

  _curr = randomLoc


  private var count = 0

  override def persist[T](payload: Payload[T]) = {}

  override protected def calcNextLocationAlgorithm(elapsed: Float): Unit = {
    count += 1

    if (count % 120 == 0) {
      randomLoc
    }
  }

  private def randomLoc: Vector2 = {
    _next = new Vector2(
      (_map.width * Math.random()).asInstanceOf[Float],
      (_map.height * Math.random()).asInstanceOf[Float]
    )
    _next
  }

}
