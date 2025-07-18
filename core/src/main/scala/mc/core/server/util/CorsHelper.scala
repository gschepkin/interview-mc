package mc.core.server.util

import cats.data.OptionT
import cats.effect.Async
import org.http4s.server.middleware.CORS
import org.http4s.{Http, HttpRoutes}

object CorsHelper:
  private type RoutesWithCors[F[_]] = Http[[_$7] =>> OptionT[F, _$7], F]

  def simpleHttp4sCORS[F[_]: Async](routes: HttpRoutes[F]): RoutesWithCors[F] =
    CORS.policy.withAllowOriginAll
      .withAllowCredentials(true)
      .withAllowOriginHost(_.host.value == "localhost")
      .apply(routes)
