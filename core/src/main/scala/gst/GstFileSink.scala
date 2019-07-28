package gst

import scalanative._
import unsafe._
import cobj._

/**
 * Write incoming data to a file in the local filesystem.
 *
 * @see [[https://gstreamer.freedesktop.org/documentation/coreelements/filesink.html?gi-language=c#filesink-page]]
 */
@CObj
class GstFileSink extends GstBaseSink {

  def location: String = getStringProp(c"location")
  def location_=(location: String): Unit = setStringProp(c"location",location)
}

object GstFileSink {
  def apply(name: String): GstFileSink = GstElementFactory.make[GstFileSink]("filesink",name).get
}
