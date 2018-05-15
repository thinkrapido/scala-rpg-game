package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.screen.GameScreen
import com.jellobird.testgame.time.Tick
import com.jellobird.testgame.utils.ScaleFactor
import com.jellobird.testgame.utils.destinations.Route
import com.jellobird.testgame.utils.movement.Movement
import com.jellobird.testgame.utils.world.{Area, Location, Range}

/**
  * Created by jbc on 23.12.16.
  */
trait MapPosition {

  this: Tick =>

  val epsilon = 0.002f

  val _last = new Area(new Location(0, 0), new Range(0, 0))

  val movement: Movement

  lazy val route: Route = (new Route).append(_last.location)

  def curr: Area = {
    _last
  }

  def setDestination(box: Area): Unit = {
    route.append(box.location)
  }
  def setDestination(vec: Vector2): Unit = {
    setDestination(new Area(new Location(vec), _last.range))
  }
  def setPath(path: List[Area]): Unit = {
    route.set(path.map(_.location))
  }

  def tick: Unit = {
    val next = route.walkTowards((_last.location, movement))
    println("%f %f %f %f" format (next.x, next.y, _last.location.x, _last.location.y))
    _last.location.moveTo(next)
  }

  def nextMapPosition(direction: Vector2): Unit = {
    val nextPos = direction.nor().scl(movement.effectiveVelocity).add(curr.location.x, curr.location.y)
    println(nextPos)
    val next = new Area(new Location(nextPos), curr.range)
    if (!GameScreen.current.map.testCollision(next)) {
      setDestination(next)
    }
  }

}
