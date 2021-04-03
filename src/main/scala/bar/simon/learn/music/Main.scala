package bar.simon.learn.music

import java.util.Locale

import bar.simon.learn.music.http.ServerBuilder
import cats.effect._

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

object Main extends IOApp {
  Locale.setDefault(new Locale("en", "US"))

  def run(args: List[String]): IO[ExitCode] = {
    implicit val ec: ExecutionContext = global

    new ServerBuilder[IO]
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}
