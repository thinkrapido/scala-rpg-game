package com.jellobird.testgame.input

import com.badlogic.gdx.Gdx
import InputKeyState._
import com.badlogic.gdx.Input.Keys

/**
  * Created by jbc on 02.12.16.
  */
case class InputKeyState(val key: Int) {

  private var _count = 0

  def count = _count

  def ++ = if ( Gdx.input.isKeyPressed(key) ) _count += 1

  def ! = {
    _count = 0
  }

  def copy: InputKeyState = {
    val out = new InputKeyState(key)
    out._count = _count
    out
  }

}

object InputKeyState {

  private val keys = Set(
    Keys.ANY_KEY,
    Keys.NUM_0,
    Keys.NUM_1,
    Keys.NUM_2,
    Keys.NUM_3,
    Keys.NUM_4,
    Keys.NUM_5,
    Keys.NUM_6,
    Keys.NUM_7,
    Keys.NUM_8,
    Keys.NUM_9,
    Keys.A,
    Keys.ALT_LEFT,
    Keys.ALT_RIGHT,
    Keys.APOSTROPHE,
    Keys.AT,
    Keys.B,
    Keys.BACK,
    Keys.BACKSLASH,
    Keys.C,
    Keys.CALL,
    Keys.CAMERA,
    Keys.CLEAR,
    Keys.COMMA,
    Keys.D,
    Keys.DEL,
    Keys.BACKSPACE,
    Keys.FORWARD_DEL,
/*
    Keys.DPAD_CENTER,
    Keys.DPAD_DOWN,
    Keys.DPAD_LEFT,
    Keys.DPAD_RIGHT,
    Keys.DPAD_UP,
*/
    Keys.CENTER,
    Keys.DOWN,
    Keys.LEFT,
    Keys.RIGHT,
    Keys.UP,
    Keys.E,
    Keys.ENDCALL,
    Keys.ENTER,
    Keys.ENVELOPE,
    Keys.EQUALS,
    Keys.EXPLORER,
    Keys.F,
    Keys.FOCUS,
    Keys.G,
    Keys.GRAVE,
    Keys.H,
    Keys.HEADSETHOOK,
    Keys.HOME,
    Keys.I,
    Keys.J,
    Keys.K,
    Keys.L,
    Keys.LEFT_BRACKET,
    Keys.M,
    Keys.MEDIA_FAST_FORWARD,
    Keys.MEDIA_NEXT,
    Keys.MEDIA_PLAY_PAUSE,
    Keys.MEDIA_PREVIOUS,
    Keys.MEDIA_REWIND,
    Keys.MEDIA_STOP,
    Keys.MENU,
    Keys.MINUS,
    Keys.MUTE,
    Keys.N,
    Keys.NOTIFICATION,
    Keys.NUM,
    Keys.O,
    Keys.P,
    Keys.PERIOD,
    Keys.PLUS,
    Keys.POUND,
    Keys.POWER,
    Keys.Q,
    Keys.R,
    Keys.RIGHT_BRACKET,
    Keys.S,
    Keys.SEARCH,
    Keys.SEMICOLON,
    Keys.SHIFT_LEFT,
    Keys.SHIFT_RIGHT,
    Keys.SLASH,
    Keys.SOFT_LEFT,
    Keys.SOFT_RIGHT,
    Keys.SPACE,
    Keys.STAR,
    Keys.SYM,
    Keys.T,
    Keys.TAB,
    Keys.U,
    Keys.UNKNOWN,
    Keys.V,
    Keys.VOLUME_DOWN,
    Keys.VOLUME_UP,
    Keys.W,
    Keys.X,
    Keys.Y,
    Keys.Z,
/*
    Keys.META_ALT_LEFT_ON,
    Keys.META_ALT_ON,
    Keys.META_ALT_RIGHT_ON,
    Keys.META_SHIFT_LEFT_ON,
    Keys.META_SHIFT_ON,
    Keys.META_SHIFT_RIGHT_ON,
    Keys.META_SYM_ON,
*/
    Keys.CONTROL_LEFT,
    Keys.CONTROL_RIGHT,
    Keys.ESCAPE,
    Keys.END,
    Keys.INSERT,
    Keys.PAGE_UP,
    Keys.PAGE_DOWN,
    Keys.PICTSYMBOLS,
    Keys.SWITCH_CHARSET,
    Keys.BUTTON_CIRCLE,
    Keys.BUTTON_A,
    Keys.BUTTON_B,
    Keys.BUTTON_C,
    Keys.BUTTON_X,
    Keys.BUTTON_Y,
    Keys.BUTTON_Z,
    Keys.BUTTON_L1,
    Keys.BUTTON_R1,
    Keys.BUTTON_L2,
    Keys.BUTTON_R2,
    Keys.BUTTON_THUMBL,
    Keys.BUTTON_THUMBR,
    Keys.BUTTON_START,
    Keys.BUTTON_SELECT,
    Keys.BUTTON_MODE,
    Keys.NUMPAD_0,
    Keys.NUMPAD_1,
    Keys.NUMPAD_2,
    Keys.NUMPAD_3,
    Keys.NUMPAD_4,
    Keys.NUMPAD_5,
    Keys.NUMPAD_6,
    Keys.NUMPAD_7,
    Keys.NUMPAD_8,
    Keys.NUMPAD_9,
    Keys.COLON,
    Keys.F1,
    Keys.F2,
    Keys.F3,
    Keys.F4,
    Keys.F5,
    Keys.F6,
    Keys.F7,
    Keys.F8,
    Keys.F9,
    Keys.F10,
    Keys.F11,
    Keys.F12
  )

  val keyStates: Set[InputKeyState] = {
    keys.map(new InputKeyState(_))
  }

  def reset: Unit = keyStates.foreach(_.!)

}
