organization in ThisBuild := "de.surfice"

version in ThisBuild := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.12"

val Version = new {
  val swog        = "0.1.0-SNAPSHOT"
  val glib        = "0.0.2-SNAPSHOT"
  val smacrotools = "0.0.8"
  val utest       = "0.6.8-SNAPSHOT"
}


lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  resolvers += Resolver.sonatypeRepo("snapshots"),
  libraryDependencies ++= Seq(
    "de.surfice" %%% "swog-cobj" % Version.swog,
    "de.surfice" %%% "scalanative-gobj" % Version.glib,
    "com.lihaoyi" %%% "utest" % Version.utest % "test"
    ),
  testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val gstreamer = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .aggregate(core,video,pluginsBase)
  .settings(commonSettings ++ dontPublish:_*)
  .settings(
    name := "scalanative-gstreamer"
  )

lazy val core = project
  .enablePlugins(ScalaNativePlugin)
  .settings(commonSettings ++ publishingSettings:_*)
  .settings(
    name := "scalanative-gstreamer-core"
  )

lazy val video = project
  .enablePlugins(ScalaNativePlugin)
  .dependsOn(core)
  .settings(commonSettings ++ publishingSettings:_*)
  .settings(
    name := "scalanative-gstreamer-video"
  )

lazy val pluginsBase = project
  .enablePlugins(ScalaNativePlugin)
  .dependsOn(video)
  .settings(commonSettings ++ publishingSettings:_*)
  .settings(
    name := "scalanative-gstreamer-plugins-base"
  )

lazy val demo = project
  .enablePlugins(ScalaNativePlugin,NBHAutoPlugin,NBHMakePlugin)
  .dependsOn(pluginsBase)
  .settings(commonSettings ++ dontPublish:_*)
  .settings(
    libraryDependencies ++= Seq(
      "de.surfice" %%% "scalanative-gtk3" % Version.glib
    )
    //nativeLinkingOptions ++= nbhNativeLinkingOptions.value
  )

lazy val dontPublish = Seq(
  publish := {},
  publishLocal := {},
  com.typesafe.sbt.pgp.PgpKeys.publishSigned := {},
  com.typesafe.sbt.pgp.PgpKeys.publishLocalSigned := {},
  publishArtifact := false,
  publishTo := Some(Resolver.file("Unused transient repository",file("target/unusedrepo")))
)

lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/scalanative-gstreamer</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/scalanative-gtk</url>
      <connection>scm:git:git@github.com:jokade/scalanative-gstreamer.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jokade</id>
        <name>Johannes Kastner</name>
        <email>jokade@karchedon.de</email>
      </developer>
    </developers>
  )
)
 
