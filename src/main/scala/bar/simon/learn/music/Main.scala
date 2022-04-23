package bar.simon.learn.music

import bar.simon.learn.music.http.ServerBuilder
import cats.effect._

import java.util.Locale

object Main extends IOApp {
  Locale.setDefault(new Locale("en", "US"))

  def run(args: List[String]): IO[ExitCode] =
    new ServerBuilder[IO]
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
