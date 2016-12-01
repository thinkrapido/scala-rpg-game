package com.jellobird.testgame.maps

import com.badlogic.gdx.math.Vector2
import com.jellobird.testgame.storage.{Payload, Repository}

/**
  * Created by jbc on 01.12.16.
  */
class LocationsRepository extends Repository[LocationEntity] {
}

object LocationsRepository {

  case class RegisterEntity(override val payload: LocationEntity) extends Payload(payload, null)
  case class AbsolutePos(override val payload: Vector2, override val uuid: String) extends Payload(payload, uuid)
  case class DeltaPos(override val payload: Vector2, override val uuid: String) extends Payload(payload, uuid)

}

