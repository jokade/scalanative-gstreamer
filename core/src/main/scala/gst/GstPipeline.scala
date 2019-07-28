package gst

import glib.gboolean

import scalanative._
import unsafe._
import cobj._

/**
 * @see [[https://gstreamer.freedesktop.org/documentation/gstreamer/gstpipeline.html?gi-language=c#GstPipeline]]
 */
@CObj
class GstPipeline extends GstBin {

  /**
   * Let this pipeline select a clock automatically.
   */
  def autoClock(): Unit = extern

  /**
   * Returns whether the pipeline will automatically flush its bus when going from READY to NULL state or not.
   */
  def getAutoFlushBus(): gboolean = extern

  /**
   * Returns the bus of this pipeline.
   */
  override def getBus(): GstBus = extern

  /**
   * Returns the clock currently used by this pipeline.
   */
  def getClock(): GstClock = extern
}

object GstPipeline {
  /**
   * Creates a new pipeline with the given name .
   */
  @name("gst_pipeline_new")
  def apply(name: CString): GstPipeline = extern

}
