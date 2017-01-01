package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.utils.ScaleFactor

/**
  * Created by jbc on 23.12.16.
  */
trait MapPosition {

  this: Tick =>

  val epsilon = 0.002f

  val _last: Vector2 = new Vector2()
  val _next: Vector2 = new Vector2()
  val _helper: Vector2 = new Vector2()

  val step: Float
  val scaleFactor: ScaleFactor

  var path: List[Vector2] = Nil

  def penalty: Float
  def currStep = step * penalty
  def nextDestination: Vector2 = path.dropWhile(_.epsilonEquals(_last, epsilon)) match {
    case Nil => _last
    case head :: _ => head
  }
  def curr: Vector2 = {
    val dest = nextDestination
    if (_last.len() == 0) _last.set(dest)
    if (dest.epsilonEquals(_last, epsilon)) _next.set(dest)
    else {
      _helper.set(dest).sub(_last)
      if (_helper.len() > currStep) {
        _helper.nor().scl(currStep)
      }
      _next.set(_helper/*.scl(scaleFactor.factor)*/.add(_last))
    }
    _last.set(_next)
  }

  def setDestination(vector: Vector2): Unit = {
    path = List(vector)
  }
  def setPath(path: List[Vector2]): Unit = {
    this.path = path
  }

  def tick: Unit = {
    _last.set(_next)
  }

}
