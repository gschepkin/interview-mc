package mc.implementation.config

import cats.effect.kernel.Sync
import pureconfig.*
import pureconfig.generic.derivation.default.*

// TODO собрать конфиг на основании application.conf
final case class AppConfig()

object AppConfig:
  def load[F[_]: Sync]: F[AppConfig] =
    Sync[F].delay(
      ConfigSource.default.loadOrThrow[AppConfig]
    )
