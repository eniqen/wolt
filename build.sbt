import com.typesafe.sbt.packager.docker.{Cmd, ExecCmd}

ThisBuild / organization := "com.wolt"
name := "wolt-assignment"
version := "0.1"
scalaVersion := "2.13.5"

scalacOptions += "-Ymacro-annotations"

libraryDependencies ++= Dependencies.live

Dependencies.CompilerPlugins.live.map(addCompilerPlugin)

resolvers += Resolver.sonatypeRepo("snapshots")

enablePlugins(JavaAppPackaging, JDKPackagerPlugin, DockerPlugin)

dockerExposedPorts ++= Seq(8080)
dockerBaseImage := "openjdk:8-jre-alpine"
dockerCommands ++= Seq(
  Cmd("USER", "root"),
  ExecCmd("RUN", "apk", "add", "--no-cache", "bash")
)

mainClass in Compile := Some("com.wolt.assignment.ConverterApp")

conflictManager := ConflictManager.latestRevision