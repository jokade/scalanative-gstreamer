import glib.guint64

import scalanative._
import unsafe._
import unsigned._

package object gst {

  type GstState = Int
  object GstState {
    val VOID_PENDING        = 0
    val NULL                = 1
    val READY               = 2
    val PAUSED              = 3
    val PLAYING             = 4
  }

  type GstStateChangeReturn = Int
  object GstStateChangeReturn {
    val FAILURE    = 0
    val SUCCESS    = 1
    val ASYNC      = 2
    val NO_PREROLL = 3
  }

  type GstMessageType = Int
  object GstMessageType {
    val UNKNOWN           :GstMessageType = 0
    val EOS               :GstMessageType = 1 << 0
    val ERROR             :GstMessageType = 1 << 1
    val WARNING           :GstMessageType = 1 << 2
    val INFO              :GstMessageType = 1 << 3
    val TAG               :GstMessageType = 1 << 4
    val BUFFERING         :GstMessageType = 1 << 5
    val STATE_CHANGED     :GstMessageType = 1 << 6
    val STATE_DIRTY       :GstMessageType = 1 << 7
    val STEP_DONE         :GstMessageType = 1 << 8
    val CLOCK_PROVIDE     :GstMessageType = 1 << 9
    val CLOCK_LOST        :GstMessageType = 1 << 10
    val NEW_CLOCK         :GstMessageType = 1 << 11
    val STRUCTURE_CHANGE  :GstMessageType = 1 << 12
    val STREAM_STATUS     :GstMessageType = 1 << 13
    val APPLICATION       :GstMessageType = 1 << 14
    val ELEMENT           :GstMessageType = 1 << 15
    val SEGMENT_START     :GstMessageType = 1 << 16
    val SEGMENT_DONE      :GstMessageType = 1 << 17
    val DURATION_CHANGED  :GstMessageType = 1 << 18
    val LATENCY           :GstMessageType = 1 << 19
    val ASYNC_START       :GstMessageType = 1 << 20
    val ASYNC_DONE        :GstMessageType = 1 << 21
    val REQUEST_STATE     :GstMessageType = 1 << 22
    val STEP_START        :GstMessageType = 1 << 23
    val QOS               :GstMessageType = 1 << 24
    val PROGRESS          :GstMessageType = 1 << 25
    val TOC               :GstMessageType = 1 << 26
    val RESET_TIME        :GstMessageType = 1 << 27
    val STREAM_START      :GstMessageType = 1 << 28
    val NEED_CONTEXT      :GstMessageType = 1 << 29
    val HAVE_CONTEXT      :GstMessageType = 1 << 30
    val EXTENDED          :GstMessageType = 1 << 31
    val DEVICE_ADDED      :GstMessageType = EXTENDED + 1
    val DEVICE_REMOVED    :GstMessageType = EXTENDED + 2
    val PROPERTY_NOTIFY   :GstMessageType = EXTENDED + 3
    val STREAM_COLLECTION :GstMessageType = EXTENDED + 4
    val STREAMS_SELECTED  :GstMessageType = EXTENDED + 5
    val REDIRECT          :GstMessageType = EXTENDED + 6
    val DEVICE_CHANGED    :GstMessageType = EXTENDED + 6
    val ANY               :GstMessageType = 0xffffffff
  }

  type GstClockTime = guint64
  object GstClockTime {
    val None :GstClockTime = ULong.MaxValue

    def isValid(time: GstClockTime): Boolean = time != None
  }

  type GstFormat = Int
  object GstFormat {
    val UNDEFINED :GstFormat = 0
    val DEFAULT   :GstFormat = 1
    val BYTES     :GstFormat = 2
    val TIME      :GstFormat = 3
    val BUFFERS   :GstFormat = 4
    val PERCENT   :GstFormat = 5
  }

  type GstSeekFlags = Int
  object GstSeekFlags {
    val NONE                :GstSeekFlags = 0
    val FLUSH               :GstSeekFlags = 1 << 0
    val ACCURATE            :GstSeekFlags = 1 << 1
    val KEY_UNIT            :GstSeekFlags = 1 << 2
    val SEGMENT             :GstSeekFlags = 1 << 3
    val TRICKMODE           :GstSeekFlags = 1 << 4
    val SKIP                :GstSeekFlags = 1 << 4
    val SNAP_BEFORE         :GstSeekFlags = 1 << 5
    val SNAP_AFTER          :GstSeekFlags = 1 << 6
    val SNAP_NEAREST        :GstSeekFlags = SNAP_BEFORE | SNAP_AFTER
    val TRICKMODE_KEY_UNITS :GstSeekFlags = 1 << 7
    val TRICKMODE_NO_AUDIO  :GstSeekFlags = 1 << 8
  }


}
