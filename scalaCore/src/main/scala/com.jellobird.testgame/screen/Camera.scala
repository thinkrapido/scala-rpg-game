package com.jellobird.testgame.screen

import com.badlogic.gdx.graphics.OrthographicCamera
import com.jellobird.testgame.maps.LocationEntity

/**
  * Created by jbc on 01.12.16.
  */
class Camera() extends OrthographicCamera {

  private var _locationEntity: LocationEntity = null
  def setLocationEntity(entity: LocationEntity) = _locationEntity = entity

  override def update(): Unit = {

    if (_locationEntity != null) {
      position.set(_locationEntity.curr, 0)
    }

    super.update()
  }

}
