package gst.plugins.base

import glib.{gint, gulong}
import gst.{GstPipeline, GstTagList}

import scalanative._
import unsafe._
import cobj._
import scala.scalanative.interop.RefZone
import scala.scalanative.runtime.Intrinsics

/**
 * Autoplug and play media from an uri.
 *
 * @see [[https://developer.gnome.org/gst-plugins-libs/stable/gst-plugins-base-plugins-playbin.html]]
 */
@CObj
class GstPlayBin extends GstPipeline {

  /**
   * The next URI that playbin will play.
   */
  def uri: String = getStringProp(c"uri")
  def uri_=(uri: String): Unit = setStringProp(c"uri",uri)

  /**
   * Returns the total number of available audio streams.
   */
  def nAudio: gint = getIntProp(c"n-audio")

  def getAudioTags(streamId: Int): GstTagList = GstTagList.getTagList(this,c"get-audio-tags",streamId)

  /**
   * Returns the total number of available video streams
   */
  def nVideo: gint = getIntProp(c"nvideo")

  /**
   * Returns the total number of available subtitle streams.
   */
  def nText: gint = getIntProp(c"n-text")

  def onAudioTagsChanged(handler: Function2[GstPlayBin,gint,Unit])(implicit refZone: RefZone, wrapper: CObjectWrapper[GstPlayBin]): gulong =
    connect2(c"audio-tags-changed",(arg1: Ptr[Byte], arg2: Ptr[Byte]) => {
      handler.apply(wrapper.wrap(arg1),if(arg2==null) 0 else arg2.toInt)
    })


  def onTextTagsChanged(handler: Function2[GstPlayBin,gint,Unit])(implicit refZone: RefZone, wrapper: CObjectWrapper[GstPlayBin]): gulong =
    connect2(c"text-tags-changed",(arg1: Ptr[Byte], arg2: Ptr[Byte]) => {
      handler.apply(wrapper.wrap(arg1),if(arg2==null) 0 else arg2.toInt)
    })

  def onVideoTagsChanged(handler: Function2[GstPlayBin,gint,Unit])(implicit refZone: RefZone, wrapper: CObjectWrapper[GstPlayBin]): gulong =
    connect2(c"video-tags-changed",(arg1: Ptr[Byte], arg2: Ptr[Byte]) => {
      handler.apply(wrapper.wrap(arg1),if(arg2==null) 0 else arg2.toInt)
    })
}
