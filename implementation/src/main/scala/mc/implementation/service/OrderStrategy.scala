package mc.implementation.service

import cats.syntax.applicative.given
import cats.syntax.traverse.given
import cats.syntax.flatMap.given
import cats.syntax.functor.given
import cats.syntax.option.given
import cats.data.NonEmptyList
import cats.effect.kernel.Sync
import io.circe.Codec
import mc.implementation.model.Position
import mc.implementation.repository.PriceRepository
import mc.implementation.service.OrderStrategy.ComputedResult
import mc.implementation.service.OrderTransformations.Transformers

/** На основании sku посчитать результат
  *
  * Подсказка: sku -> Items -> Positions -> ComputedResult
  */
trait OrderStrategy[F[_]]:
  def compute(nelSku: NonEmptyList[String]): F[ComputedResult]

object OrderStrategy:
  final case class ComputedResult(
      total: Double,
      positions: List[Position]
  ) derives Codec

  def make[F[_]: Sync](
      rep: PriceRepository[F],
      transforms: Transformers[F[_]]
  ): OrderStrategy[F] =
    Impl(rep, transforms)

  final class Impl[F[_]: Sync](
      rep: PriceRepository[F],
      transforms: Transformers[F[_]]
  ) extends OrderStrategy[F]:
    override def compute(nelSku: NonEmptyList[String]): F[ComputedResult] = ???
