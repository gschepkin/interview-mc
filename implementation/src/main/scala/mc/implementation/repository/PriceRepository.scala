package mc.implementation.repository

import cats.syntax.functor.given
import cats.effect.Ref
import cats.effect.kernel.Sync
import mc.implementation.model.Item

trait PriceRepository[F[_]]:
  def getItem(sku: String): F[Option[Item]]
  def updateItem(sku: String, item: Item): F[Unit]
  def removeItem(sku: String): F[Unit]
  def getAll: F[Map[String, Item]]

object PriceRepository:
  private val initPrices: Map[String, Item] =
    Map(
      "apple"      -> Item("apple", 100.5),
      "banana"     -> Item("banana", 55.5),
      "orange"     -> Item("orange", 80.5),
      "grape"      -> Item("grape", 120.5),
      "watermelon" -> Item("watermelon", 300.5),
      "pineapple"  -> Item("pineapple", 250.5),
      "mango"      -> Item("mango", 200.5),
      "strawberry" -> Item("strawberry", 150.5),
      "blueberry"  -> Item("blueberry", 160.5),
      "kiwi"       -> Item("kiwi", 90.5)
    )

  def make[F[_]: Sync]: F[PriceRepository[F]] =
    Ref.of[F, Map[String, Item]](initPrices).map(ref => Impl[F](ref))

  private final class Impl[F[_]: Sync](ref: Ref[F, Map[String, Item]]) extends PriceRepository[F]:
    def getItem(sku: String): F[Option[Item]] =
      ref.get.map(_.get(sku))

    def updateItem(sku: String, item: Item): F[Unit] =
      ref.update(_.updated(sku, item))

    def removeItem(sku: String): F[Unit] =
      ref.update(_ - sku)

    def getAll: F[Map[String, Item]] = ref.get
