package bar.simon.learn.music

import java.util.Locale

import bar.simon.learn.music.http.ServerBuilder
import com.typesafe.scalalogging.StrictLogging
import org.slf4j.Logger
import zio._

object Main extends App with StrictLogging {
  Locale.setDefault(new Locale("en", "US"))

  implicit final val slf4jLogger: Logger = logger.underlying

  override def run(args: List[String]): URIO[ZEnv, ExitCode] =
    ServerBuilder(slf4jLogger).resource
      .use(_ => Task.never)
      .tapError(logFailure)
      .exitCode

  def logFailure(error: Throwable): UIO[ExitCode] =
    UIO.succeed(logger.error(s"Error encountered in the server: ${error.getMessage}")).as(ExitCode.failure)

}
