// This example is adapted from the GStreamer Basic Tutorial 5 "GUI Toolkit Integration"
// https://gstreamer.freedesktop.org/documentation/tutorials/basic/toolkit-integration.html?gi-language=c
package gsttutorial5

import scalanative._
import unsafe._
import unsigned._
import cairo.CairoCtx
import glib.{GTimeout, gint}
import gst._
import gtk._
import gst.plugins.base.GstPlayBin
import gst.video.GstVideoOverlay
import gtk.gdk.GdkQuartzWindow

import scala.scalanative.interop.RefZone
import scala.util.Success

class Player private(implicit refZone: RefZone) {

  private var _state: GstState = GstState.VOID_PENDING
  private var _duration: GstClockTime = GstClockTime.None

  private lazy val playbin = GstElementFactory.make[GstPlayBin]("playbin","playbin").get

  val sink = GstFileSink("out")
  sink.location = "out.mp4"


  private lazy val (slider,sliderHandlerId) = {
    val slider = GtkScale.withRange(GtkOrientation.HORIZONTAL, 0, 100, 1)
    (slider,slider.onValueChanged(sliderChanged))
  }

  private lazy val streamsList = GtkTextView()


  def init(): Player = {
    playbin.uri = "https://www.freedesktop.org/software/gstreamer-sdk/data/media/sintel_trailer-480p.webm"

    createUI()

    playbin.setState(GstState.PLAYING)
    playbin.onVideoTagsChanged(tagsCallback)
    playbin.onAudioTagsChanged(tagsCallback)
    playbin.onTextTagsChanged(tagsCallback)

    val bus = playbin.getBus()
    bus.addSignalWatch()
    bus.onMessage("application", applicationCallback)
    bus.onMessage("state-changed", stateChanged)
    bus.onMessage("eos", eosCallback)
    bus.onMessage("error",errorCallback)
    bus.unref()

    GTimeout.add(1000,refreshUI)

    this
  }

  private def createUI(): Unit = {

    val mainWindow = GtkWindow()
    mainWindow.title = "GStreamer Demo"
    mainWindow.onDestroy(exit)

    val videoWindow = GtkDrawingArea()
    videoWindow.onRealize(realize)
    videoWindow.onDraw(draw)

    val playButton = GtkButton.fromIconName("media-playback-start", GtkIconSize.SMALL_TOOLBAR)
    playButton.onClicked(play)

    val pauseButton = GtkButton.fromIconName("media-playback-pause", GtkIconSize.SMALL_TOOLBAR)
    pauseButton.onClicked(pause)

    val stopButton = GtkButton.fromIconName("media-playback-stop", GtkIconSize.SMALL_TOOLBAR)
    stopButton.onClicked(stop)

    streamsList.editable = true

    val controls = GtkBox(GtkOrientation.HORIZONTAL,0)
    controls.packStart(playButton,false,false,2.toUInt)
    controls.packStart(pauseButton,false,false,2.toUInt)
    controls.packStart(stopButton,false,false,2.toUInt)
    controls.packStart(slider,true,true,2.toUInt)

    val mainHBox = GtkBox(GtkOrientation.HORIZONTAL, 0)
    mainHBox.packStart(videoWindow,true,true,0.toUInt)
    mainHBox.packStart(streamsList,false,false,2.toUInt)

    val mainBox = GtkBox(GtkOrientation.VERTICAL, 0)
    mainBox.packStart(mainHBox,true,true,0.toUInt)
    mainBox.packStart(controls, false, false, 0.toUInt)

    mainWindow.add(mainBox)
    mainWindow.setDefaultSize(640,480)
    mainWindow.showAll()

  }


  private def draw(videoWindow: GtkDrawingArea, ctx: CairoCtx): Boolean = {
    if(_state < GstState.PAUSED) {
      ctx.setSourceRgb(0.0,0.0,0.0)
      ctx.rectangle(0.0,0.0,videoWindow.getAllocatedWidth().toDouble,videoWindow.getAllocatedHeight().toDouble)
      ctx.fill()
    }
    false
  }

  private def realize(widget: GtkWidget): Unit = {
    val win = widget.getWindow()
    win.ensureNative()

    GstVideoOverlay(playbin).setWindowHandle( GdkQuartzWindow.getNSView(win.handle) )
  }

  def exit(): Unit = {
    stop()
    Gtk.mainQuit()
  }

  def play(): Unit = {
    playbin.setState(GstState.PLAYING)
  }

  def pause(): Unit = {
    playbin.setState(GstState.PAUSED)
  }

  def stop(): Unit = {
    playbin.setState(GstState.READY)
  }

  private def sliderChanged(): Unit = {
    val value = slider.getValue()
    playbin.seekTime(value)
  }

  private def stateChanged(msg: GstMessage): Unit =
    if(msg.isSource(playbin)) {
      val (oldState, newState, _) = msg.parseStateChanged()
      _state = newState
      if(oldState==GstState.READY && newState == GstState.PAUSED)
        refreshUI()
    }

  private def refreshUI(): Boolean =
    if(_state < GstState.PAUSED)
      true
    else {
      updateDuration()
      updateRange()
      true
    }

  private def updateDuration() =
    if(!GstClockTime.isValid(_duration)) playbin.queryDuration().map{ d =>
      _duration = d.toULong
      val seconds = d / 1e9
      slider.setRange(0.0,seconds)
    }

  private def updateRange(): Unit =
    playbin.queryPosition() match {
      case Success(current) =>
        slider.blockHandler(sliderHandlerId)
        slider.setValue(current / 1e9)
        slider.unblockHandler(sliderHandlerId)
      case _ =>
    }

  private def applicationCallback(msg: GstMessage): Unit =
    if(msg.isName(c"tags-changed")) {
      /* TODO */
    }

  private def eosCallback(msg: GstMessage): Unit = {
    playbin.setState(GstState.READY)
  }

  private def errorCallback(msg: GstMessage): Unit = {
    /* TODO */
  }

  private def tagsCallback(playbin: GstPlayBin, stream: gint): Unit = {
    // We are in a GStreamer working thread, so we notify the main
    // thread of this event through a message in the bus
    playbin.postApplicationMessage(playbin,c"tags-changed")
  }
}

object Player {
  def apply()(implicit refZone: RefZone): Player = refZone.hold(new Player).init()
}