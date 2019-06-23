package gst

import de.surfice.smacrotools.debug
import glib.utils.GZone

import scalanative._
import unsafe._
import cobj._
import scala.util.{Failure, Success, Try}

/**
 * Create GstElements from a factory.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/GstElementFactory.html]]
 */
@CObj
object GstElementFactory {
  def make(factoryName: CString, name: CString): Ptr[Byte] = extern

  def make[T<:GstElement](factoryName: String, name: String)(implicit wrapper: CObjectWrapper[T]): Try[T] = GZone{ implicit z =>
    val elem = make(toCString(factoryName),toCString(name))
    if(elem==null)
      Failure(new RuntimeException(s"could not create element '$factoryName'"))
    else
      Success(wrapper.wrap(elem))
  }

}
