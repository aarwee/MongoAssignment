name := """assignment"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "org.reactivemongo" %% "reactivemongo" % "0.11.11",
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B3-SNAPSHOT",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.1.1-2",
  specs2 % Test

)

routesGenerator := InjectedRoutesGenerator
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

coverageExcludedPackages :="<empty>;router\\..*;"