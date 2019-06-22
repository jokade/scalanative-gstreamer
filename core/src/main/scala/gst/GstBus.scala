package gst

import glib.gulong
import glib.utils.GZone

import scalanative._
import unsafe._
import cobj._
import scala.scalanative.interop.RefZone

/**
 * Asynchronous message bus subsystem.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/GstBus.html]]
 */
@CObj
class GstBus extends GstObject {

  /**
   * Adds a bus signal watch to the default main context with the default priority (G_PRIORITY_DEFAULT).
   */
  def addSignalWatch(): Unit = extern

  def timedPopFiltered(timeout: GstClockTime, types: GstMessageType): GstMessage = extern

  /**
   * Executes the provided event handler when a bus message of `messageType` is detected.
   *
   * @param messageType message type to listen to (the full signal name is this value prefixed with `message::`
   * @param handler handler exectued when the specified message occurs
   * @param refZone
   * @param wrapper
   */
  def onMessage(messageType: String, handler: Function1[GstMessage,Unit])(implicit refZone: RefZone, wrapper: CObjectWrapper[GstMessage]): gulong =
    GZone{ implicit z =>
      val signal = toCString(s"message::$messageType")
      connect1(signal,(arg1: Ptr[Byte]) => handler(wrapper.wrap(arg1)))
    }
}
