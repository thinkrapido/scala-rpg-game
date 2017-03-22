package com.jellobird.testgame.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}

/**
  * Created by jbc on 01.12.16.
  */
class AssetManager extends com.badlogic.gdx.assets.AssetManager {

  private val TAG = classOf[AssetManager].getSimpleName

  private val filePathResolver = new InternalFileHandleResolver()

  def unloadAsset(file: String) = {
    if(isLoaded(file)) {
      unload(file)
    }
    else {
      Gdx.app.debug(TAG, "Asset is not loaded. Nothing to unload. (%s)".format(file))
    }
  }

  def loadMapAsset(file: String): Unit = {
    val clazz = classOf[TiledMap]
    if (filePathResolver.resolve(file).exists()) {
      setLoader(clazz, new TmxMapLoader(filePathResolver))
      load(file, clazz)
      finishLoadingAsset(file)
      Gdx.app.debug(TAG, "%s loaded!: %s".format(clazz, file))
    }
    else {
      Gdx.app.debug(TAG, "%s doesn't exist!: %s".format(clazz, file))
    }
  }

  def getMapAsset(file: String): TiledMap = {
    if (isLoaded(file)) {
      get(file).asInstanceOf[TiledMap]
    }
    else {
      Gdx.app.debug(TAG, "%s is not loaded!: %s".format(classOf[TiledMap], file))
      null
    }
  }

  def loadTextureAsset(file: String): Unit = {
    val clazz = classOf[Texture]
    if (filePathResolver.resolve(file).exists()) {
      setLoader(clazz, new TextureLoader(filePathResolver))
      load(file, clazz)
      finishLoadingAsset(file)
      Gdx.app.debug(TAG, "%s loaded!: %s".format(clazz, file))
    }
    else {
      Gdx.app.debug(TAG, "%s doesn't exist!: %s".format(clazz, file))
    }
  }

  def getTextureAsset(file: String): Option[Texture] = {
    if (isLoaded(file)) {
      Some(get(file).asInstanceOf[Texture])
    }
    else {
      Gdx.app.debug(TAG, "%s is not loaded!: %s".format(classOf[Texture], file))
      Some(null)
    }
  }
}
