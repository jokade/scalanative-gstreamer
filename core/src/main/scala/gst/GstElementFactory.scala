package gst

import de.surfice.smacrotools.debug
import glib.utils.GZone

import scalanative._
import unsafe._
import cobj._

/**
 * Create GstElements from a factory.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/GstElementFactory.html]]
 */
@CObj
@debug
object GstElementFactory {
  def make(factoryName: CString, name: CString): Ptr[Byte] = extern

  def make[T<:GstElement](factoryName: String, name: String)(implicit wrapper: CObjectWrapper[T]): T = GZone{ implicit z =>
    wrapper.wrap( make(toCString(factoryName),toCString(name)) )
  }

}
