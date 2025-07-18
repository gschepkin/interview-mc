package mc.implementation

import cats.data.NonEmptyList
import cats.effect.kernel.Async
import cats.syntax.functor.given
import cats.syntax.applicative.given
import cats.syntax.flatMap.given
import cats.effect.{IO, IOApp, Resource}
import sttp.tapir.generic.auto.*
import mc.core.server.HttpServer
import mc.core.server.endpoint.HealthCheck
import mc.implementation.config.AppConfig
import mc.implementation.repository.PriceRepository
import mc.implementation.service.OrderStrategy.ComputedResult
import mc.implementation.service.{OrderStrategy, OrderTransformations}
import sttp.tapir.*
import sttp.tapir.json.circe.*

object Application extends IOApp.Simple:
  override def run: IO[Unit] =
    program[IO].useForever

  private def program[F[_]: Async] =
    def init =
      for
        config    <- AppConfig.load[F]
        transforms = OrderTransformations.list

        health   = HealthCheck.healthEndpoint(config.app.version)
        strategy = strategyEndpoint[F](???) // TODO need OrderStrategy

        endpoints = List(health, strategy)

        _ <- Async[F].unit
      yield (config.http, endpoints)

    for
      (config, routes) <- Resource.eval(init)
      _                <- HttpServer.run(config, routes)
    yield ()

  private def strategyEndpoint[F[_]: Async](strategy: OrderStrategy[F]) =
    endpoint.get
      .in("strategy")
      .in(query[List[String]]("sku"))
      .out(jsonBody[ComputedResult])
      .serverLogicSuccess { sku =>
        ???
      }
