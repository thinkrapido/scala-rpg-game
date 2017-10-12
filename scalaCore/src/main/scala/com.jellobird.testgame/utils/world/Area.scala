package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 10/11/2017.
  */
case class Area(location: Location, range: Range) extends Scale[Area] {

  val top = location.y + range.height
  val left = location.x
  val right = location.x + range.width
  val bottom = location.y

  def boundingBox(area: Area): Area = {

    val top = Math.max(this.top, area.top)
    val left = Math.min(this.left, area.left)
    val right = Math.max(this.right, area.right)
    val bottom = Math.min(this.bottom, area.bottom)

    Area(Location(left, bottom), Range(right - left, top - bottom))
  }

  def intersect(area: Area): Area = {

    val top = Math.min(this.top, area.top)
    val left = Math.max(this.left, area.left)
    val right = Math.min(this.right, area.right)
    val bottom = Math.max(this.bottom, area.bottom)

    Area(Location(left, bottom), Range(right - left, top - bottom))
  }

  def hit(location: Location): Boolean =
    location.x >= left && location.x <= right && location.y >= bottom && location.y <= top

  def overlap(area: Area): Boolean =
    intersect(area).range.area == 0

  override def scale(factor: Float) = Area(location.scale(factor), range.scale(factor))

  def move(location: Location) = Area(this.location.move(location), range)
  def moveBy(location: Location) = Area(this.location.moveBy(location), range)

  def rearange(range: Range) = Area(location, this.range.move(range))
  def rearangeBy(range: Range) = Area(location, this.range.moveBy(range))

  def rotate(rad: Float) = Area(location.rotate(rad), range)

  def transform(location: Location, factor: Float) = Area(this.location.move(location), range.scale(factor))
  def transformBy(location: Location, factor: Float) = Area(this.location.moveBy(location), range.scale(factor))

}
