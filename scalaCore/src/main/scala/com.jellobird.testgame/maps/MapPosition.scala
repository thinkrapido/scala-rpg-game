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

  var _last = Area(Location(0, 0), Range(0, 0))
  var _next = _last
  var _helper = _last

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
    if (implicitly[Distance](_last.location).distance == 0) _last = Area(dest.location, _last.range)
    if (dest.location.matches(_last.location, epsilon)) _next = Area(dest.location, _next.range)
    else {
      _helper = Area(dest.location.moveBy(_last.location.inverse), _helper.range)
      if (implicitly[Distance](_helper.location).distance > currStep) {
        _helper = Area(implicitly[Distance](_helper.location).normal.scale(currStep), _helper.range)
      }
      val factor = scaleFactor.factor
      if (factor > lastScaleFactor) {
        lastScaleFactor = factor
      }
      _next = Area(_helper.location.scale(lastScaleFactor).move(_last.location), _next.range)
    }
    _next
    //println(dest, path)
    //dest
  }

  def setDestination(box: Area): Unit = {
    path = List(box)
  }
  def setDestination(vec: Vector2): Unit = {
    setDestination(Area(vec, _last.range))
  }
  def setPath(path: List[Area]): Unit = {
    this.path = path
  }

  def tick: Unit = {
    _last = Area(_next.location, _last.range)
    lastScaleFactor = 0f
  }

  def nextMapPosition(direction: Vector2): Unit = {
    val nextPos = direction.nor().scl(currStep).add(curr.location)

    val next = Area(nextPos, curr.range)

    if (!GameScreen.current.map.testCollision(next)) {
      setDestination(next)
    }
  }

}
