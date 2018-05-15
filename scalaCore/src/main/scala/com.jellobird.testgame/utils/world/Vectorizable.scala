package com.jellobird.testgame.utils.world

/**
  * Created by Romeo Disca on 5/15/2018.
  */
trait Vectorizable[T <: Vector] extends Vector {

  override def normal = new Location(vector nor)
  override def copy = new Location(vec)
  override def add(other: Vector): Vector = new Location(vec.add(other.vec))
  override def sub(other: Vector): Vector = new Location(vec.sub(other.vec))

}
