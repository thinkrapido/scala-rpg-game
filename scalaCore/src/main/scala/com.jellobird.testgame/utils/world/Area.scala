package com.jellobird.testgame.utils.world

import com.jellobird.testgame.utils.world.Area.AreaRelation

import scala.util.{Success, Failure, Try}

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Area(location: Location, range: Range) extends Scale[Area] {

  val top = location.y + range.height
  val left = location.x
  val right = location.x + range.width
  val bottom = location.y

  def hit(location: Location): Boolean =
    location.x >= left && location.x <= right && location.y >= bottom && location.y <= top

  private def boundingBox(area: Area): Area = {

    val top = Math.max(this.top, area.top)
    val left = Math.min(this.left, area.left)
    val right = Math.max(this.right, area.right)
    val bottom = Math.min(this.bottom, area.bottom)

    Area(Location(left, bottom), Range(right - left, top - bottom))
  }

  private def intersect(area: Area): Area = {

    val top = Math.min(this.top, area.top)
    val left = Math.max(this.left, area.left)
    val right = Math.min(this.right, area.right)
    val bottom = Math.max(this.bottom, area.bottom)

    Area(Location(left, bottom), Range(right - left, top - bottom))
  }

  private def overlap(area: Area): Boolean =
    intersect(area).range.area == 0

  def relate(other: Area): AreaRelation = {
    import Area._
    val bBox = boundingBox(other)
    Try(intersect(other)) match {
      case Success(intersection) =>
        if (intersection == this) Contains(this, other)
        else if (intersection == other) Contains(other, this)
        else Overlap(intersection, bBox)
      case Failure(_) => None
    }
  }

  override def scale(factor: Float) = Area(location.scale(factor), range.scale(factor))

  def move(location: Location) = Area(this.location.move(location), range)
  def moveBy(location: Location) = Area(this.location.moveBy(location), range)

  def rearange(range: Range) = Area(location, this.range.move(range))
  def rearangeBy(range: Range) = Area(location, this.range.moveBy(range))

  def rotate(rad: Float) = Area(location.rotate(rad), range)

  def transform(location: Location, factor: Float) = Area(this.location.move(location), range.scale(factor))
  def transformBy(location: Location, factor: Float) = Area(this.location.moveBy(location), range.scale(factor))

}

object Area {

  sealed abstract trait AreaRelation
  case object None extends AreaRelation
  case class Contains(inner: Area, outer: Area) extends AreaRelation
  case class Overlap(intersection: Area, boundingBox: Area) extends AreaRelation

}
