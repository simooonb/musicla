package bar.simon.learn.music.http

import cats.Show
import cats.effect.{Blocker, ContextShift, Resource, Sync}
import cats.implicits.catsSyntaxEither
import eu.timepit.refined.pureconfig._
import eu.timepit.refined.types.net.NonSystemPortNumber
import eu.timepit.refined.types.string.NonEmptyString
import pureconfig._
import pureconfig.error.ConfigReaderException
import pureconfig.generic.auto._
import pureconfig.module.catseffect.syntax._

final case class ServerConfiguration(
    host: NonEmptyString,
    port: NonSystemPortNumber
)

object ServerConfiguration {

  def load[F[_]](blocker: Blocker)(implicit F: Sync[F], cs: ContextShift[F]): Resource[F, ServerConfiguration] =
    Resource.liftF(F.fromEither(ConfigSource.default.load[ServerConfiguration].leftMap(ConfigReaderException(_))))

  implicit final val show: Show[ServerConfiguration] = pprint.apply(_).render
}
