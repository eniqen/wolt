import sbt._

object Dependencies {
  type GroupId = String
  type Version = String

  trait Module {
    def groupId: GroupId

    def live: List[ModuleID]

    def withVersion(version: Version): String => ModuleID = groupId %% _ % version

    def withVersionSimple(version: Version): String => ModuleID = groupId % _ % version

    def withTest(module: ModuleID): ModuleID = module % Test
  }

  object Versions {
    val betterMonadicForPlugin = "0.3.1"
    val catsEffect             = "3.1.0"
    val catsRetry              = "2.1.0"
    val enumeratum             = "1.7.0"
    val log4Cats               = "2.1.0"
    val logback                = "1.2.3"
    val circe                  = "0.13.0"
    val fs2                    = "3.0.2"
    val http4s                 = "1.0.0-M21"
    val newtype                = "0.4.4"
    val kindProjector          = "0.11.3"
    val pureConfig             = "0.15.0"
    val refined                = "0.9.26"
    val tapir                  = "0.18.0-M18"

    val scalatest              = "3.1.0"
    val scalamock              = "5.1.0"
  }

  object typelevel extends Module {
    def groupId: GroupId = "org.typelevel"

    val live = List(
      withVersion(Versions.catsEffect)("cats-effect"),
      withVersion(Versions.log4Cats)("log4cats-slf4j")
    )
  }

  object http4s extends Module {
    def groupId: GroupId = "org.http4s"

    val live: List[sbt.ModuleID] =
      List(
        "http4s-core",
        "http4s-dsl",
        "http4s-blaze-server",
        "http4s-blaze-client",
        "http4s-circe"
      ).map(withVersion(Versions.http4s))
  }

  object fs2 extends Module {
    def groupId: GroupId = "co.fs2"

    def live: List[sbt.ModuleID] = List(
      "fs2-core",
      "fs2-io"
    ).map(withVersion(Versions.fs2))
  }

  object circe extends Module {
    def groupId: GroupId = "io.circe"

    def live: List[sbt.ModuleID] = List(
      "circe-generic",
      "circe-core",
      "circe-parser",
      "circe-generic-extras"
    ).map(withVersion(Versions.circe))
  }

  object pureconfig extends Module {
    def groupId: GroupId = "com.github.pureconfig"

    def live: List[sbt.ModuleID] = List("pureconfig").map(withVersion(Versions.pureConfig))
  }

  object refined extends Module {
    override def groupId: GroupId = "eu.timepit"

    override def live: List[sbt.ModuleID] = List("refined").map(withVersion(Versions.refined))
  }

  object logback extends Module {
    def groupId: GroupId = "ch.qos.logback"

    def live: List[sbt.ModuleID] = List("logback-classic").map(withVersionSimple(Versions.logback))
  }

  object estatico extends Module {
    def groupId: GroupId = "io.estatico"

    def live: List[sbt.ModuleID] = List("newtype").map(withVersion(Versions.newtype))
  }

  object enumeratum extends Module {
    override def groupId: GroupId = "com.beachape"

    override def live: List[sbt.ModuleID] = List("enumeratum", "enumeratum-circe").map(withVersion(Versions.enumeratum))
  }

  object scalatest extends Module {
    override def groupId: GroupId = "org.scalatest"

    override def live: List[sbt.ModuleID] = List("scalatest").map(withVersion(Versions.scalatest)).map(withTest)
  }

  object scalamock extends Module {
    override def groupId: GroupId = "org.scalamock"

    override def live: List[sbt.ModuleID] = List("scalamock").map(withVersion(Versions.scalamock)).map(withTest)
  }

  object tapir extends Module {
    override def groupId: GroupId = "com.softwaremill.sttp.tapir"

    override def live: List[sbt.ModuleID] = List(
      "tapir-core",
      "tapir-openapi-docs",
      "tapir-openapi-circe-yaml",
      "tapir-swagger-ui-http4s",
      "tapir-json-circe",
      "tapir-http4s-server",
      "tapir-openapi-circe-yaml",
      "tapir-enumeratum"
    ).map(withVersion(Versions.tapir))
  }

  val live: List[sbt.ModuleID] =
    List(
      typelevel,
      http4s,
      fs2,
      enumeratum,
      circe,
      pureconfig,
      logback,
      estatico,
      refined,
      scalatest,
      scalamock
//      tapir
    )
      .flatMap(_.live)

  object CompilerPlugins {

    object olegpy extends Module {
      def groupId: GroupId = "com.olegpy"

      def live: List[sbt.ModuleID] = List("better-monadic-for").map(withVersion(Versions.betterMonadicForPlugin))
    }

    object typelevel extends Module {
      def groupId: GroupId = "org.typelevel"

      def live: List[sbt.ModuleID] = List(groupId % "kind-projector" % Versions.kindProjector cross CrossVersion.full)
    }

    val live: List[sbt.ModuleID] = List(olegpy, typelevel).flatMap(_.live)
  }
}
