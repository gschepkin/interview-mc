import sbt.*

object Dependencies {
  val CompileParams = "compile->compile;test->test"
  
  val All: Seq[ModuleID] =
    Logging.all ++
      Json.all ++
      Http.all ++
      Configuration.all ++
      Typelevel.all

  object Configuration {
    val pureConfig: ModuleID        = "com.github.pureconfig" %% "pureconfig-core"           % Versions.pureconfig
    val pureGenericConfig: ModuleID = "com.github.pureconfig" %% "pureconfig-generic-scala3" % Versions.pureconfig

    val all: Seq[ModuleID] = pureConfig :: pureGenericConfig :: Nil
  }

  object Typelevel {
    val cats       = "org.typelevel" %% "cats-core"     % Versions.cats
    val catsEffect = "org.typelevel" %% "cats-effect"   % Versions.catsEffect
    val fs2        = "co.fs2"        %% "fs2-core"      % Versions.fs2
    val tofuCe3    = "tf.tofu"       %% "tofu-core-ce3" % Versions.tofu

    val all: Seq[ModuleID] = Seq(cats, catsEffect, fs2, tofuCe3)
  }

  object Json {
    val circeCore: ModuleID    = "io.circe" %% "circe-core"    % Versions.circe
    val circeParser: ModuleID  = "io.circe" %% "circe-parser"  % Versions.circe
    val circeGeneric: ModuleID = "io.circe" %% "circe-generic" % Versions.circe

    val all: Seq[ModuleID] = circeCore :: circeParser :: circeGeneric :: Nil
  }

  object Http {
    val sttpFs2Client: ModuleID = "com.softwaremill.sttp.client4" %% "fs2"   % Versions.sttp
    val sttpCirce: ModuleID     = "com.softwaremill.sttp.client4" %% "circe" % Versions.sttp

    val tapirOpenApiDoc: ModuleID         = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"      % Versions.tapir
    val tapirOpenApiDocGenerate: ModuleID = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % Versions.tapir
    val tapirCore: ModuleID               = "com.softwaremill.sttp.tapir" %% "tapir-core"              % Versions.tapir
    val tapirJson: ModuleID               = "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % Versions.tapir
    val tapirSttp: ModuleID               = "com.softwaremill.sttp.tapir" %% "tapir-sttp-client"       % Versions.tapir
    val tapirHttp4s: ModuleID             = "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"     % Versions.tapir

    val http4sDsl: ModuleID    = "org.http4s" %% "http4s-dsl"          % Versions.http4s
    val http4sServer: ModuleID = "org.http4s" %% "http4s-ember-server" % Versions.http4s

    val all: Seq[ModuleID] =
      Seq(
        tapirOpenApiDoc,
        tapirOpenApiDocGenerate,
        tapirCore,
        tapirJson,
        tapirHttp4s,
        http4sDsl,
        http4sServer,
        sttpCirce,
        sttpFs2Client,
        tapirJson,
        tapirSttp,
      )
  }

  object Logging {
    val loggingStructured      = "tf.tofu"       %% "tofu-logging-structured"       % Versions.tofuLogging
    val loggingLayout          = "tf.tofu"       %% "tofu-logging-layout"           % Versions.tofuLogging
    val loggingDerivation      = "tf.tofu"       %% "tofu-logging-derivation"       % Versions.tofuLogging
    val loggingLogstashLogback = "tf.tofu"       %% "tofu-logging-logstash-logback" % Versions.tofuLogging
    val logbackClassic         = "ch.qos.logback" % "logback-classic"               % Versions.logback

    val all: Seq[ModuleID] =
      Seq(loggingStructured, loggingLayout, loggingDerivation, loggingLogstashLogback, logbackClassic)
  }
}
