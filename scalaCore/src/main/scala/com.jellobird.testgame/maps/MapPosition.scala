package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.utils.ScaleFactor

/**
  * Created by jbc on 23.12.16.
  */
trait MapPosition {

  this: Tick =>

  val epsilon = 0.002f

  val _last = BoundingBox()
  val _next = BoundingBox()
  val _helper = BoundingBox()

  val step: Float
  val scaleFactor: ScaleFactor

  var path: List[BoundingBox] = Nil

  def penalty: Float
  def currStep = step * penalty
  def nextDestination: BoundingBox = path.dropWhile(_.position.epsilonEquals(_last.position, epsilon)) match {
    case Nil => _last
    case head :: _ => head
  }
  var lastScaleFactor = 0f
  def curr: BoundingBox = {
    val dest = nextDestination
    if (_last.position.len() == 0) _last.position.set(dest.position);
    if (dest.position.epsilonEquals(_last.position, epsilon)) _next.position.set(dest.position)
    else {
      _helper.position.set(dest.position).sub(_last.position)
      if (_helper.position.len() > currStep) {
        _helper.position.nor().scl(currStep)
      }
      val factor = scaleFactor.factor
      if (factor > lastScaleFactor) {
        lastScaleFactor = factor
      }
      _next.position.set(_helper.position.scl(lastScaleFactor).add(_last.position))
    }
    _next
    //println(dest, path)
    //dest
  }

  def setDestination(box: BoundingBox): Unit = {
    path = List(box)
  }
  def setDestination(vec: Vector2): Unit = {
    setDestination(BoundingBox.build.move(vec).tile(_last.tile).get)
  }
  def setPath(path: List[BoundingBox]): Unit = {
    this.path = path
  }

  def tick: Unit = {
    _last.position.set(_next.position)
    lastScaleFactor = 0f
  }

  def nextMapPosition(direction: Vector2): Unit = {
    val nextPos = direction.nor().scl(currStep).add(curr.position)

    val next = BoundingBox.build.move(nextPos).tile(curr.tile).get

    if (!GameScreen.current.map.testCollision(next)) {
      setDestination(next)
    }
  }

}
