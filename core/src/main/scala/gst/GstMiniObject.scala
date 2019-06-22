package gst

import glib.{GRefCounter, gint, gpointer, guint}

import scalanative._
import unsafe._
import cobj._

/**
 * Lightweight base class for the GStreamer object hierarchy.
 */
@CObj
class GstMiniObject extends GRefCounter {
  @returnsThis
  override def ref(): this.type = extern
  override def unref(): Unit = extern
}

object GstMiniObject {
  // type, refcount, lockstate, flags, copy, dispose, free
  type GstMiniObjectStruct = CStruct7[gpointer,gint,gint,guint,CFuncPtr,CFuncPtr,CFuncPtr]
}
