package gst

import scala.scalanative.unsafe._
import scala.scalanative.unsigned.UInt

object GstTag {
  sealed trait TagValue[T] {
    def tag: TagType[T]
    def value: Option[T]
  }
  case class StringValue(tag: StringTag, value: Option[String]) extends TagValue[String]
  case class UIntValue(tag: UIntTag, value: Option[UInt]) extends TagValue[UInt] {
    def intValue: Option[Int] = value.map(_.toInt)
  }

  sealed trait TagType[T] {
    def name: CString
    def apply(list: GstTagList): TagValue[T]
  }
  case class StringTag(name: CString) extends TagType[String] {
    def apply(list: GstTagList) = StringValue(this,list.getString(name))
  }
  case class UIntTag(name: CString) extends TagType[UInt] {
    def apply(list: GstTagList) = UIntValue(this,list.getUInt(name))
  }

  val Title               = StringTag(c"title")
  val TitleSortname       = StringTag(c"title-sortname")
  val Artist              = StringTag(c"artist")
  val ArtistSortname      = StringTag(c"artist-sortname")
  val Album               = StringTag(c"album")
  val AlbumSortname       = StringTag(c"album-sortname")
  val AlbumArtist         = StringTag(c"album-artist")
  val AlbumArtistSortname = StringTag(c"album-artist-sortname")
  val Composer            = StringTag(c"composer")
  val Conductor           = StringTag(c"conductor")
  // DATE
  // DATE_TIME
  val Genre               = StringTag(c"genre")
  val Comment             = StringTag(c"comment")

  val Location            = StringTag(c"location")

  val Organization        = StringTag(c"organization")

  val Codec               = StringTag(c"codec")
  val VideoCodec          = StringTag(c"video-codec")
  val AudioCodec          = StringTag(c"audio-codec")
  val SubtitleCodec       = StringTag(c"subtitle-codec")
  val ContainerFormat     = StringTag(c"container-format")
  val Bitrate             = UIntTag(c"bitrate")
  val NominalBitrate      = UIntTag(c"nominal-bitrate")
  val MinimumBitrate      = UIntTag(c"minimum-bitrate")
  val MaximumBitrate      = UIntTag(c"maximum-bitrate")
  val Serial              = UIntTag(c"serial")
  val Encoder             = StringTag(c"Encoder")
  val EncoderVersion      = UIntTag(c"encoder-version")

  lazy val standardTags = Seq(
    Title,
    TitleSortname,
    Artist,
    ArtistSortname,
    Composer,
    Conductor,
    Genre,
    Comment,
    Location,
    Organization,
    Codec,
    VideoCodec,
    AudioCodec,
    SubtitleCodec,
    ContainerFormat,
    Bitrate,
    NominalBitrate,
    MinimumBitrate
  )

  lazy val standardTagMap = standardTags.map(tag => fromCString(tag.name) -> tag).toMap
}
