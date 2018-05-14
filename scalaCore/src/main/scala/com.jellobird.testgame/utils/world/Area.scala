package com.jellobird.testgame.utils.world

import java.util.Objects

import com.jellobird.testgame.utils.world.Area.AreaRelation

import scala.util.{Failure, Success, Try}

/**
  * Created by Romeo Disca on 10/11/2017.
  */
class Area(val location: Location, val range: Range) {

  def this() = this(new Location(0,0), new Range(0, 0))

  val top = location.y + range.height
  val left = location.x
  val right = location.x + range.width
  val bottom = location.y

  def hit(location: Location): Boolean =
    location.x >= left && location.x <= right && location.y >= bottom && location.y <= top

  private def boundingBox(other: Area): Area = {

    val top = Math.max(this.top, other.top)
    val left = Math.min(this.left, other.left)
    val right = Math.max(this.right, other.right)
    val bottom = Math.min(this.bottom, other.bottom)

    new Area(new Location(left, bottom), new Range(right - left, top - bottom))
  }

  private def intersect(other: Area): Option[Area] = {

    if (
      contains(new Location(other.top, other.left)) ||
      contains(new Location(other.top, other.right)) ||
      contains(new Location(other.bottom, other.left)) ||
      contains(new Location(other.bottom, other.right))
    ) {
      val top = Math.max(this.top, other.top)
      val left = Math.max(this.left, other.left)
      val right = Math.min(this.right, other.right)
      val bottom = Math.min(this.bottom, other.bottom)

      Some(new Area(new Location(left, top), new Range(right - left, bottom - top)))

      }
    else None
  }

  private def contains(other: Area): Boolean = {
    this.top >= other.top && this.bottom <= other.bottom && this.left <= other.bottom && this.right >= other.bottom
  }
  private def contains(point: Location): Boolean = {
    return point.x >= this.left && point.x <= this.right && point.y >= this.top && point.y <= this.bottom
  }

  private def overlap(other: Area): Boolean =
    intersect(other) match {
      case Some(_) => true
      case None => false
    }

  def relate(other: Area): AreaRelation = {
    import Area._
    val bBox = boundingBox(other)
    intersect(other) match {
      case Some(intersection) =>
        if (intersection.equals(this)) Contains(this, other)
        else if (intersection.equals(other)) Contains(other, this)
        else Overlap(intersection, bBox)
      case _ => None
    }                                                            }

  def scale(factor: Float) = { location.scale(factor); range.scale(factor); this }

  def move(location: Location) = { this.location.moveTo(location); this }
  def moveBy(location: Location) = { this.location.moveBy(location); this }

  def rearrange(range: Range) = { this.range.moveTo(range); this }
  def rearrangeBy(range: Range) = { this.range.moveBy(range); this }

  def rotate(rad: Float) = { location.rotateTo(rad); this }

  def transform(location: Location, factor: Float) = { this.location.moveTo(location); range.scale(factor); this }
  def transformBy(location: Location, factor: Float) = { this.location.moveBy(location); range.scale(factor); this }

  override def hashCode(): Int = Objects.hash(location, range)
  override def equals(obj: scala.Any): Boolean = obj match {
    case a: Area => a.location.equals(location) && a.range.equals(range)
    case _ => false
  }

}

object Area {

  sealed abstract trait AreaRelation
  case object None extends AreaRelation
  case class Contains(inner: Area, outer: Area) extends AreaRelation
  case class Overlap(intersection: Area, boundingBox: Area) extends AreaRelation

}
