package mc.implementation.service

import cats.effect.kernel.Sync
import mc.implementation.model.Position

/** TODO
  *   - Удалить все позиции с count <= 0
  *   - Ограничить максимальное количество одной позиции
  *   - Применить скидку на все товары (проценты)
  *   - Логировать итог
  */
object OrderTransformations:
  type Transformers[F[_]] = List[Transform[F]]

  type Transform[F[_]] = Position => F[Option[Position]]
  
  // TODO example: List(removeZero, restrictMax, applyDiscount, logging)
  def list[F[_]: Sync]: Transformers[F] = ???
