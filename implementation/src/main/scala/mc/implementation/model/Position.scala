package mc.implementation.model

import io.circe.Codec

final case class Position(
    sku: String,
    count: Int,
    unitPrice: Double
) derives Codec
