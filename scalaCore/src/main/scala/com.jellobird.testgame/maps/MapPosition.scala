package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.utils.ScaleFactor
import com.jellobird.testgame.utils.world.{Area, Distance, Location, Range}

/**
  * Created by jbc on 23.12.16.
  */
trait MapPosition {

  this: Tick =>

  val epsilon = 0.002f

  val _last = new Area(new Location(0, 0), new Range(0, 0))
  val _next = _last
  val _helper = _last

  val step: Float
  val scaleFactor: ScaleFactor

  var path: List[Area] = Nil

  def penalty: Float
  def currStep = step * penalty
  def nextDestination: Area = path.dropWhile(_.location.epsilonEquals(_last.location, epsilon)) match {
    case Nil => _last
    case head :: _ => head
  }
  var lastScaleFactor = 0f
  def curr: Area = {
    val dest = nextDestination
    if (_last.location.len() == 0) _last.location.set(dest.location);
    if (dest.location.epsilonEquals(_last.location, epsilon)) _next.location.set(dest.location)
    else {
      _helper.location.set(dest.location).sub(_last.location)
      if (_helper.location.len() > currStep) {
        _helper.location.nor().scl(currStep)
      }
      val factor = scaleFactor.factor
      if (factor > lastScaleFactor) {
        lastScaleFactor = factor
      }
      _next.location.set(_helper.location.scl(lastScaleFactor).add(_last.location))
    }
    _next
    //println(dest, path)
    //dest
  }

  def setDestination(box: Area): Unit = {
    path = List(box)
  }
  def setDestination(vec: Vector2): Unit = {
    setDestination(new Area(vec, _last.range))
  }
  def setPath(path: List[Area]): Unit = {
    this.path = path
  }

  def tick: Unit = {
    _last.location.set(_next.location)
    lastScaleFactor = 0f
  }

  def nextMapPosition(direction: Vector2): Unit = {
    val nextPos = direction.nor().scl(currStep).add(curr.location)

    val next = new Area(nextPos, curr.range)

    if (!GameScreen.current.map.testCollision(next)) {
      setDestination(next)
    }
  }

}
