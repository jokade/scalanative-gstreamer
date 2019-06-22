package gst

import glib.gboolean
import gobject.GObject

import scalanative._
import unsafe._
import cobj._

/**
 * Base class for the GStreamer object hierarchy.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/GstObject.html]]
 */
@CObj
class GstObject extends GObject {

  def getName(): CString = extern
  def setName(name: CString): gboolean = extern

  @returnsThis
  override def ref(): this.type = extern
  override def unref(): Unit = extern
}
