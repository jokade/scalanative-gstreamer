package gst

import scalanative._
import unsafe._
import cobj._

/**
 * The opaque structure of a GstSample.
 * A sample contains a typed memory block and the associated timing information. It is mainly used to exchange buffers with an application.
 *
 * @see [[https://gstreamer.freedesktop.org/documentation/gstreamer/gstsample.html?gi-language=c#GstSample]]
 */
@CObj
class GstSample extends GstMiniObject {

}
