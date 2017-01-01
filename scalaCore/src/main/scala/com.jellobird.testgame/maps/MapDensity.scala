package com.jellobird.testgame.maps

/**
  * Created by jbc on 23.12.16.
  */
trait MapDensity {

  def density: Float = { 1f } ensuring (d => d >= 0 && d <= 1)

}
