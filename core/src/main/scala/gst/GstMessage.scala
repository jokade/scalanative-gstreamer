package gst

import glib.{GError, GLib, GRefCounter, gint, gpointer, guint32, guint64}

import scalanative._
import unsafe._
import cobj._

/**
 * Lightweight objects to signal the application of pipeline events.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/gstreamer-GstMessage.html]]
 */
@CObj
class GstMessage extends GstMiniObject {
  /**
   * Extracts the old and new states from this message.
   *
   * @param oldState
   * @param newState
   * @param pending
   */
  def parseStateChanged(oldState: Ptr[GstState], newState: Ptr[GstState], pending: Ptr[GstState]): Unit = extern

  /**
   * Returns the old and new states from this message.
   *
   * @return A triple (oldState, newState, pending)
   */
  def parseStateChanged(): (GstState,GstState,GstState) = {
    val oldState = stackalloc[GstState]
    val newState = stackalloc[GstState]
    val pending = stackalloc[GstState]
    parseStateChanged(oldState,newState,pending)
    (!oldState,!newState,!pending)
  }

  /**
   * Extracts the GError and debug string from this message.
   * The values returned in the output arguments are copies; the caller must free them when done.
   * @param error
   * @param debugInfo
   */
  def parseError(error: Ptr[Ptr[GError.GErrorStruct]], debugInfo: Ptr[CString]): Unit = extern

  def parseError(): String = {
    val err = stackalloc[Ptr[GError.GErrorStruct]]
    parseError(err,null)
    val errmsg = fromCString((!err)._3)
    GError.free(!err)
    errmsg
  }

  /**
   * Returns the pointer to the source of this message.
   */
  def source(): gpointer = GstMessage.ext.snhelper_gst_message_src(this.__ptr)

  /**
   * Returns true if the specified object is the source of this message.
   *
   * @param obj
   * @return
   */
  def isSource(obj: GstObject): Boolean = source() == obj.__ptr

  /**
   * Access the structure of this message.
   */
  def getStructure(): GstStructure = extern

  /**
   * Returns the name of this message
   */
  // TODO: avoid wrappers
  def name(): CString = getStructure().getName()

  /**
   * Returns true if the name of this message is euql to the specified CString.
   *
   * @param s
   */
  def isName(s: CString): Boolean =  true //libc.string.strcmp(name(),s) == 0


}

object GstMessage {

  /**
   * Create a new application-typed message. GStreamer will never create these messages.
   *
   * @param src
   * @param structure
   * @return
   */
  @name("gst_message_new_application")
  def application(src: GstObject, structure: GstStructure): GstMessage = extern

  /**
   * Creates an empty application-typed message with the given name.
   *
   * @param src
   * @param name
   * @return
   */
  // TODO: avoid wrappers
  def applicationEmpty(src: GstObject, name: CString): GstMessage =  application(src,GstStructure.empty(name))


  @extern
  object ext {
    def snhelper_gst_message_src(msg: Ptr[Byte]): gpointer = extern
  }
}


trait ApplicationMessage {
  self: Singleton =>

  def name: CString

  def apply(src: GstObject): GstMessage = GstMessage.applicationEmpty(src,name)
/*
  def unapply(msg: GstMessage): Boolean =
    if(msg==null)
      false
    else
      libc.string.strcmp(name,msg.name()) == 0
*/
def unapply(msg: GstMessage)(implicit wrapper: CObjectWrapper[GstObject]): Option[GstObject] =
  if(msg==null)
    None
  else if(libc.string.strcmp(name,msg.name())==0)
    Some(wrapper.wrap(msg.source()))
  else
    None
}