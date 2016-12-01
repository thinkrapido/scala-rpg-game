package com.jellobird.testgame.storage

/**
  * Created by jbc on 01.12.16.
  */
class Payload[T](val payload: T, val uuid: String, val timestamp: Timestamp = new Timestamp)
