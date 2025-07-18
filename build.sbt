import sbt.Keys.scalacOptions

ThisBuild / scalaVersion := Versions.scala
ThisBuild / organization := "mc"
ThisBuild / version      := "0.1.1"

lazy val settings = Seq(
  scalacOptions ++= Seq(
    "-language:implicitConversions",
    "-Ykind-projector:underscores",
    "-Wunused:imports",
  ),
)

lazy val `core` =
  project
    .settings(settings)
    .settings(ThisBuild / versionScheme := Some("early-semver"))
    .settings(libraryDependencies ++= Dependencies.All)

lazy val `implementation` =
  project
    .settings(publish / skip := true)
    .settings(settings)
    .dependsOn(`core` % Dependencies.CompileParams)

lazy val `interview-project` =
  project
    .in(file("."))
    .settings(settings)
    .settings(publish / skip := true)
    .settings(Compile / mainClass := Some("mc.implementation.Application"))
    .dependsOn(`implementation` % Dependencies.CompileParams)
    .aggregate(`core`)
