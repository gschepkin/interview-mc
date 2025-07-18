package mc.core.server.config

import com.comcast.ip4s.{Host, Port}
import pureconfig.ConfigReader
import pureconfig.generic.derivation.default.*

final case class HttpServerConfig(
    host: Host,
    port: Port,
    swaggerPath: String
) derives ConfigReader

object HttpServerConfig:
  given ConfigReader[Host] = ConfigReader.fromStringOpt(Host.fromString)
  given ConfigReader[Port] = ConfigReader.fromStringOpt(Port.fromString)
