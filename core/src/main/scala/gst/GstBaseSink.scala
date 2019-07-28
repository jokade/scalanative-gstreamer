package gst

import glib.{gboolean, gint64, guint, guint64}

import scalanative._
import unsafe._
import cobj._

/**
 * Base class for all sink elements in GStreamer.
 *
 * @see [[https://gstreamer.freedesktop.org/documentation/base/gstbasesink.html?gi-language=c]]
 */
@CObj
class GstBaseSink extends GstElement {

  /**
   * Returns the number of bytes that the sink will pull when it is operating in pull mode.
   */
  def getBlocksize(): guint = extern

  /**
   * Checks if this sink is currently configured to drop buffers which are outside the current segment.
   */
  def getDropOutOfSegment(): gboolean = extern

  /**
   * Get the last sample that arrived in the sink and was used for preroll or for rendering. This property can be used to generate thumbnails.
   */
  def getLastSample(): GstSample = extern

  /**
   * Returns the currently configured latency.
   */
  def getLatency(): GstClockTime = extern

  /**
   * Returns the maximum amount of bits per second that the sink will render.
   */
  def getMaxBitrate(): guint64 = extern

  /**
   * The maximum time in nanoseconds that a buffer can be late before it is dropped and not rendered.
   * A value of -1 means an unlimited time.
   */
  def getMaxLateness(): gint64 = extern

  /**
   * Returns the processing deadline of this sink.
   */
  def getProcessingDeadline(): GstClockTime = extern

  /**
   * Returns the render delay for this sink.
   */
  def getRenderDelay(): GstClockTime = extern

  /**
   * Returns various statistics for this sink.
   */
  def getStats(): GstStructure = extern

  /**
   * Returns true if this sink is configured to synchronize against the clock.
   */
  def getSync(): gboolean = extern

  /**
   * Returns the time that will be inserted between frames to control the maximum buffers per second.
   */
  def getThrottleTime(): guint64 = extern

  /**
   * Returns the synchronization offset for this sink.
   */
  def getTsOffset(): GstClockTimeDiff = extern

  /**
   * Returns true if the sink is configured to perform asynchronous state changes.
   */
  def isAsyncEnabled(): gboolean = extern

  /**
   *  Returns true if the sink is configured to store the last received sample.
   */
  def isLastSampleEnabled(): gboolean = extern

  /**
   * Returns true if the sink is configured to perform Quality-of-Service.
   */
  def isQosEnabled(): gboolean = extern

  /**
   * If set to true, the sink performs all state changes asynchronously.
   */
  def setAsyncEnabled(enabled: gboolean): Unit = extern

  /**
   * Set the number of bytes that the sink will pull when it is operating in pull mode.
   *
   * @param blocksize blocksize in bytes
   */
  def setBlocksize(blocksize: guint): Unit = extern

  /**
   * Configure this sink to drop buffers which are outside the current segment
   *
   * @param dropOutOfSegment
   */
  def setDropOutOfSegment(dropOutOfSegment: gboolean): Unit = extern

  /**
   * Configures sink to store the last received sample in the last-sample property.
   */
  def setLastSampleEnabled(enabled: gboolean): Unit = extern

  /**
   * Set the maximum amount of bits per second the sink will render.
   *
   * @param maxBitrate
   */
  def setMaxBitrate(maxBitrate: guint64): Unit = extern

  /**
   * Sets the maximum lateness value for this sink.
   *
   * @param maxLateness
   */
  def setMaxLateness(maxLateness: gint64): Unit = extern

  /**
   * Maximum amount of time (in nanoseconds) that the pipeline can take for processing the buffer.
   *
   * @param processingDeadline
   */
  def setProcessingDeadline(processingDeadline: GstClockTime): Unit = extern

  /**
   * Configures this sink to send Quality-of-Service events upstream.
   *
   * @param enabled
   */
  def setQosEnabled(enabled: gboolean): Unit = extern

  /**
   * Sets the render delay for this source.
   *
   * @param delay
   */
  def setRenderDelay(delay: GstClockTime): Unit = extern

  /**
   * Configures sink to synchronize on the clock or not.
   *
   * @param sync
   */
  def setSync(sync: gboolean): Unit = extern

  /**
   * Set the time that will be inserted between rendered buffers.
   *
   * @param throttle
   */
  def setThrottleTime(throttle: guint64): Unit = extern

  /**
   * Adjust the synchronisation of sink with offset.
   *
   * @param offset
   */
  def setTsOffset(offset: GstClockTimeDiff): Unit = extern

  /**
   * This function will wait for preroll to complete and will then block until time is reached.
   *
   * @param time
   * @param jitter
   */
  def wait(time: GstClockTime, jitter: GstClockTimeDiff): Unit = extern

  /**
   * This function will block until time is reached.
   * @param time
   * @param jitter
   */
  def waitClock(time: GstClockTime, jitter: GstClockTimeDiff): Unit = extern

  def waitPreroll(): GstFlowReturn = extern
}
