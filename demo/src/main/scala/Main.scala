import gst.Gst
import gsttutorial5.Player
import gtk._

object Main {

  def main(args: Array[String]): Unit = {
    import scala.scalanative.interop.RefZone.Implicits.Global

    Gtk.init(args)
    Gst.init(args)

    Player()
    scalanative.runtime.GC.collect()

    Gtk.main()

    // free resources
    //playbin.setState(GstState.NULL)
    //playbin.unref()
  }



}
