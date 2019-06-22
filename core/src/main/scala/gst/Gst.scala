package gst

import glib.utils.GZone

import scalanative._
import unsafe._
import cobj._

/**
 * @see [[https://developer.gnome.org/gstreamer/stable/gstreamer-Gst.html]]
 */
@CObj
object Gst {
  def init(argc: Int, argv: Ptr[CString]): Unit = extern

  def init(args: Array[String]): Unit = GZone{ implicit z =>
    val argv = argsToArgv(args)
    init(args.length,argv)
  }

  private def argsToArgv(args: Array[String])(implicit zone: Zone): Ptr[CString] = {
    val argv = alloc[CString](args.length)
    val argvPtr = alloc[Ptr[CString]]
    !argvPtr = argv
    for (i <- args.indices) {
      !(argv + i) = toCString(args(i))
    }
    argv
  }

}
