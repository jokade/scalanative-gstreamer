package gst

import glib.GError

import scalanative._
import unsafe._
import cobj._

/**
 * @see [[https://developer.gnome.org/gstreamer/stable/gstreamer-GstParse.html]]
 */
@CObj
object GstParse {
  def launch(pipelineDescription: CString)(implicit error: ResultPtr[GError]): GstElement = extern

}
