package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2

/**
  * Created by Romeo Disca on 10/9/2017.
  */
case class BoundingBox(x: Float = 0, y: Float = 0, width: Float = 0, height: Float = 0) {
  val x2 = { x + width }
  val y2 = { y + height }
  val upperLeft = new Vector2(x, y2)
  val upperRight = new Vector2(x2, y2)
  val bottomLeft = new Vector2(x, y)
  val bottomRight = new Vector2(x2, y)
  def test(pos: Vector2): Boolean = pos.x >= x && pos.x <= x2 && pos.y >= y && pos.y <= y2
  def test(box: BoundingBox): Boolean = {
    !((box.x > x && box.x > x2) || (box.x2 < x && box.x2 < x2) || (box.y > y && box.y > y2) || (box.y2 < y && box.y2 < y2))
  }
  def test(pos: Vector2, tile: Tile): Boolean = test(BoundingBox(pos.x, pos.y, tile.width, tile.height))
  val position = new Vector2(x, y)
  val tile = Tile(width, height)
  def move(pos: Vector2) = BoundingBox(pos.x, pos.y, width, height)
  def move(x: Float, y: Float) = BoundingBox(x, y, width, height)
  def scale(factor: Float): BoundingBox = scale(factor, factor)
  def scale(factorX: Float, factorY: Float) = BoundingBox(x * factorX, y * factorY, width * factorX, height * factorY)
  def resize(tile: Tile) = BoundingBox(x, y, tile.width, tile.height)
}
object BoundingBox {
  class BoundingBoxBuilder {
    private var boundingBox = BoundingBox()
    def get = boundingBox
    def move(pos: Vector2) = { boundingBox = boundingBox.move(pos); this }
    def move(x: Float, y: Float) = { boundingBox = boundingBox.move(x, y); this }
    def height(height: Float) = { boundingBox = BoundingBox(boundingBox.x, boundingBox.y, boundingBox.width, height); this }
    def width(width: Float) = { boundingBox = BoundingBox(boundingBox.x, boundingBox.y, width, boundingBox.height); this }
    def tile(tile: Tile) = { boundingBox = BoundingBox(boundingBox.x, boundingBox.y, tile.width, tile.height); this }
  }

  def build = new BoundingBoxBuilder()
}
