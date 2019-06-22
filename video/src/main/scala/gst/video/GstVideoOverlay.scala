package gst.video

import glib.{gboolean, gint, guintptr}
import gst.GstElement

import scalanative._
import unsafe._
import cobj._

/**
 * @see [[https://gstreamer.freedesktop.org/documentation/video/gstvideooverlay.html?gi-language=c]]
 */
@CObj
class GstVideoOverlay {
  def expose(): Unit = extern
  def setRenderRectangle(x: gint, y: gint, width: gint, height: gint): gboolean = extern
  def setWindowHandle(handle: Ptr[Byte]): Unit = extern
}

object GstVideoOverlay {
  def apply(element: GstElement): GstVideoOverlay = new GstVideoOverlay(element.__ptr)
}
