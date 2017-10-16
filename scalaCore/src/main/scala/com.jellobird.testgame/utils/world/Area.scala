package com.jellobird.testgame.utils.world

import com.jellobird.testgame.utils.world.Area.AreaRelation

import scala.util.{Success, Failure, Try}

/**
  * Created by Romeo Disca on 10/11/2017.
  */
class Area(val location: Location, val range: Range) {

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

    location.moveTo(implicitly[Location](left, bottom))
    range.moveTo(implicitly[Range](right - left, top - bottom))

    this
  }

  private def intersect(area: Area): Area = {

    val top = Math.min(this.top, area.top)
    val left = Math.max(this.left, area.left)
    val right = Math.min(this.right, area.right)
    val bottom = Math.max(this.bottom, area.bottom)

    location.moveTo(implicitly[Location](left, bottom))
    range.moveTo(implicitly[Range](right - left, top - bottom))

    this
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

  def scale(factor: Float) = { location.scale(factor); range.scale(factor); this }

  def move(location: Location) = { this.location.moveTo(location); this }
  def moveBy(location: Location) = { this.location.moveBy(location); this }

  def rearrange(range: Range) = { this.range.moveTo(range); this }
  def rearrangeBy(range: Range) = { this.range.moveBy(range); this }

  def rotate(rad: Float) = { location.rotateTo(rad); this }

  def transform(location: Location, factor: Float) = { this.location.moveTo(location); range.scale(factor); this }
  def transformBy(location: Location, factor: Float) = { this.location.moveBy(location); range.scale(factor); this }

}

object Area {

  sealed abstract trait AreaRelation
  case object None extends AreaRelation
  case class Contains(inner: Area, outer: Area) extends AreaRelation
  case class Overlap(intersection: Area, boundingBox: Area) extends AreaRelation

}
