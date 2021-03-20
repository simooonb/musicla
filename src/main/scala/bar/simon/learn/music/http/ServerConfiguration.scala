package bar.simon.learn.music.http

import cats.Show
import eu.timepit.refined.pureconfig._
import eu.timepit.refined.types.net.NonSystemPortNumber
import eu.timepit.refined.types.string.NonEmptyString
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import zio.Task

final case class ServerConfiguration(
    host: NonEmptyString,
    port: NonSystemPortNumber
)

object ServerConfiguration {
  def load: Task[ServerConfiguration] =
    Task.effect(ConfigSource.default.loadOrThrow[ServerConfiguration])

  implicit final val show: Show[ServerConfiguration] = pprint.apply(_).render
}
