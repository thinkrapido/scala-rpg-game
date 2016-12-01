package com.jellobird.testgame.storage

/**
  * Created by jbc on 01.12.16.
  */
trait Entity {

  val UUID: String = java.util.UUID.randomUUID.toString

  def handle[T](payload: Payload[T]): Unit

}
