package gst

import glib.utils.GZone
import glib.{gboolean, gint64}

import scalanative._
import unsafe._
import cobj._
import scala.util.{Failure, Success, Try}

/**
 * Abstract base class for all pipeline elements.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/GstElement.html]]
 */
@CObj
class GstElement extends GstObject {

  def setState(state: GstState): GstStateChangeReturn = extern

  /**
   * Returns the bus of this element.
   */
  def getBus(): GstBus = extern

  /**
   * Post a message on the element's GstBus. This function takes ownership of the message;
   * if you want to access the message after this call, you should add an additional reference before calling.
   *
   * @param msg message to post
   * @return true if the message was successfully posted
   */
  def postMessage(msg: GstMessage): gboolean = extern

  /**
   * Posts an empty application-typed message with the specified name.
   *
   * This convenience wrapper is equivalent to
   * ```postMessage(GstMessage.applicationEmpty(src,name))```
   *
   * @param src
   * @param name
   * @return
   */
  def postApplicationMessage(src: GstObject, name: CString): gboolean =
    postMessage(GstMessage.applicationEmpty(src,name))

  /**
   * Queries this element for the total stream duration in nanoseconds.
   *
   * @param format
   * @param duration A location at which to store the result
   * @return true if the query was successful
   */
  def queryDuration(format: GstFormat, duration: Ptr[gint64]): gboolean = extern

  /**
   * Returns the total stream duration in nanoseconds.
   *
   * @param format
   */
  def queryDuration(format: GstFormat = GstFormat.TIME): Try[gint64] = {
    val duration = stackalloc[gint64]
    if( queryDuration(format,duration) )
      Success(!duration)
    else
      Failure(new RuntimeException("could not query duration"))
  }

  /**
   * Queries this element for the stream position in nanoseconds.
   *
   * @param format
   * @param position Alocation at which to store the result
   * @return true if the query was successful
   */
  def queryPosition(format: GstFormat, position: Ptr[gint64]): gboolean = extern

  /**
   * Returns the stream position in nanoseconds.
   *
   * @param format
   */
  def queryPosition(format: GstFormat = GstFormat.TIME): Try[gint64] = {
    val pos = stackalloc[gint64]
    if( queryPosition(format,pos) )
      Success(!pos)
    else
      Failure(new RuntimeException("could not query position"))
  }

  /**
   * Simple API to perform a seek on the given element, meaning it just seeks to the given position relative to the start of the stream.
   *
   * @param format a GstFormat to execute the seek in, such as [[GstFormat.TIME]]
   * @param seekFlags seek options; playback applications will usually want to use GST_SEEK_FLAG_FLUSH | GST_SEEK_FLAG_KEY_UNIT here
   * @param seekPos position to seek to (relative to the start); if you are doing a seek in GST_FORMAT_TIME this value is in nanoseconds
   * @return
   */
  def seekSimple(format: GstFormat, seekFlags: GstSeekFlags, seekPos: gint64): gboolean = extern

  /**
   * Performs a seek to the specified position in seconds.
   *
   * @param seconds
   * @param seekOptions
   */
  def seekTime(seconds: Double, seekOptions: GstSeekFlags = GstSeekFlags.FLUSH | GstSeekFlags.KEY_UNIT): gboolean =
    seekSimple(GstFormat.TIME,seekOptions, Math.round(seconds * 1e9))
}
