package mc.implementation.model

import io.circe.Codec

final case class Item(
    sku: String,
    price: Double
) derives Codec
