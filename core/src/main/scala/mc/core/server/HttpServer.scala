package mc.core.server

import cats.effect.Resource
import cats.effect.kernel.Async
import mc.core.server.config.HttpServerConfig
import mc.core.server.util.CorsHelper
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.{Router, Server}
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUIOptions
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object HttpServer:
  def run[F[_]: Async](
      config: HttpServerConfig,
      routes: List[ServerEndpoint[Any, F]]
  ): Resource[F, Server] =
    val swaggerEndpoint =
      SwaggerInterpreter(swaggerUIOptions = SwaggerUIOptions.default.pathPrefix(List("admin")))
        .fromServerEndpoints[F](
          endpoints = routes,
          title = "open-api",
          version = "1.0.0"
        )

    val httpRoutes =
      CorsHelper.simpleHttp4sCORS(
        Http4sServerInterpreter[F]().toRoutes(routes ++ swaggerEndpoint)
      )

    val app = Router("/" -> httpRoutes).orNotFound

    EmberServerBuilder
      .default[F]
      .withHost(config.host)
      .withPort(config.port)
      .withHttpApp(app)
      .build
