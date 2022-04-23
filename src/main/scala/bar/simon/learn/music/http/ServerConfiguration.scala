package bar.simon.learn.music.http

import cats.Show
import cats.effect.{Resource, Sync}
import cats.implicits.catsSyntaxEither
import eu.timepit.refined.pureconfig._
import eu.timepit.refined.types.net.NonSystemPortNumber
import eu.timepit.refined.types.string.NonEmptyString
import pureconfig._
import pureconfig.error.ConfigReaderException
import pureconfig.generic.auto._

final case class ServerConfiguration(
    host: NonEmptyString,
    port: NonSystemPortNumber
)

object ServerConfiguration {

  def load[F[_]](implicit F: Sync[F]): Resource[F, ServerConfiguration] = {
    val config = F  .fromEither(ConfigSource.default.load[ServerConfiguration].leftMap(ConfigReaderException(_)))
    Resource.liftF(config)
  }

  implicit final val show: Show[ServerConfiguration] = pprint.apply(_).render
}
