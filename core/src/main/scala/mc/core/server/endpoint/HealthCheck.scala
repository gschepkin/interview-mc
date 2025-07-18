package mc.core.server.endpoint

import cats.syntax.applicative.given
import cats.effect.*
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.circe.*
import sttp.tapir.server.ServerEndpoint.Full
import io.circe.Codec as CirceCodec

object HealthCheck:
  final case class HealthResponse(status: String, version: String) derives CirceCodec

  def healthEndpoint[F[_]: Sync](version: String): Full[Unit, Unit, Unit, Unit, HealthResponse, Any, F] =
    endpoint.get
      .in("health")
      .out(jsonBody[HealthResponse])
      .serverLogicSuccess(_ => HealthResponse("ok", version).pure[F])
