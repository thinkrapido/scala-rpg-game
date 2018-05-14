package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.utils.ScaleFactor
import com.jellobird.testgame.utils.world.{Area, Location, Range}

/**
  * Created by jbc on 23.12.16.
  */
trait MapPosition {

  this: Tick =>

  val epsilon = 0.002f

  val _last = new Area(new Location(0, 0), new Range(0, 0))
  val _next = _last

  val step: Float
  val scaleFactor: ScaleFactor

  var path: List[Area] = Nil

  def penalty: Float
  def currStep = step * penalty
  def nextDestination: Area = path.dropWhile(_.location.matches(_last.location, epsilon)) match {
    case Nil => _last
    case head :: _ => head
  }
  var lastScaleFactor = 0f
  def curr: Area = {
    val dest = nextDestination
    if (dest.location.matches(_last.location, epsilon)) {
      _next.location.moveTo(dest.location)
      _last.location.moveTo(dest.location)
    }
    else {
      val _helper: Area = new Area()
      _helper.location.moveTo(dest.location).moveBy(_last.location.copy.inverse)
      if (_helper.location.distance > currStep) {
        _helper.location.normal.scale(currStep)
      }
      val factor = scaleFactor.factor
      if (factor > lastScaleFactor) {
        lastScaleFactor = factor
      }
      _helper.location.scale(lastScaleFactor)
      _next.location.moveBy(_helper.location)
      _last.location.moveTo(_next.location)
    }
    //println(dest, path)
    _next
    //dest
  }

  def setDestination(box: Area): Unit = {
    path = List(box)
  }
  def setDestination(vec: Vector2): Unit = {
    setDestination(new Area(new Location(vec), _last.range))
  }
  def setPath(path: List[Area]): Unit = {
    this.path = path
  }

  def tick: Unit = {
    _last.location.moveTo(_next.location)
    lastScaleFactor = 0f
  }

  def nextMapPosition(direction: Vector2): Unit = {
    val nextPos = direction.nor().scl(currStep).add(curr.location.x, curr.location.y)
    val next = new Area(new Location(nextPos), curr.range)
    if (!GameScreen.current.map.testCollision(next)) {
      setDestination(next)
    }
  }

}
