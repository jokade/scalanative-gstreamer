package gst

import glib.{GLib, GRefCounter, gboolean, gint, guint}

import scalanative._
import unsafe._
import unsigned._
import cobj._
import scala.scalanative.unsigned.UInt

/**
 * List of tags and values used to describe media metadata.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/gstreamer-GstTagList.html]]
 */
@CObj
class GstTagList extends GstMiniObject {
  /**
   * Returns the number of tags in this list.
   */
  def nTags(): gint = extern

  /**
   * Get the name of the tag at index.
   *
   * @param idx
   */
  def nthTagName(idx: Int): CString = extern

  def getString(tag: CString, value: Ptr[CString]): gboolean = extern

  def getString(tag: CString): Option[String] = {
    val ptr = stackalloc[CString]
    if( getString(tag,ptr) ) {
      val s = Some(fromCString(!ptr))
      GLib.free(!ptr)
      s
    }
    else
      None
  }

  @name("gst_tag_list_get_uint")
  def getUInt(tag: CString, value: Ptr[UInt]): gboolean = extern

  def getUInt(tag: CString): Option[UInt] = {
    val ptr = stackalloc[UInt]
    if( getUInt(tag,ptr) ) {
      Some(!ptr)
    }
    else
      None
  }

  /**
   * Returns an array with the names of all tags in this list.
   */
  def tagNames(): Array[String] = {
    val n = size
    val names = Array.ofDim[String](n)
    for(i<-0 until n)
      names(i) = fromCString( nthTagName(i) )
    names
  }

  def standardTags(): Array[GstTag.TagValue[_]] = tagNames() flatMap { name => GstTag.standardTagMap.get(name) } map ( t => t.apply(this) )

  @inline final def isEmpty: Boolean = __ptr == null

  def size: Int = if(isEmpty) 0 else nTags()

}

object GstTagList {

  def getTagList(elem: GstElement, name: CString, streamId: Int): GstTagList =
    new GstTagList( ext.snhelper_gst_get_taglist(elem.__ptr,name,streamId) )

  @extern
  object ext {
    def snhelper_gst_get_taglist(elem: Ptr[Byte], signal: CString, streamId: Int): Ptr[Byte] = extern
  }


}