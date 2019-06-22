package gst

import de.surfice.smacrotools.debug
import glib.gboolean

import scalanative._
import unsafe._
import cobj._

/**
 * Generic structure containing fields of names and values.
 *
 * @see [[https://developer.gnome.org/gstreamer/stable/gstreamer-GstStructure.html#GstStructure]]
 */
@CObj
class GstStructure {

  def getName(): CString = extern

  def hasName(): gboolean = extern
}

object GstStructure {
  /**
   * Creates a new, empty GstStructure with the given `name`.
   *
   * @param name
   */
  @name("gst_structure_new_empty")
  def empty(name: CString): GstStructure = extern
}
