package bar.simon.learn.music.http

import bar.simon.learn.music.Main
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.http.questions.QuestionsCodecs
import cats.effect.{ContextShift, IO, Timer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.parser.decode

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class EndToEndTest extends AnyWordSpec with Matchers with QuestionsCodecs {

  implicit val timer: Timer[IO]     = IO.timer(ExecutionContext.global)
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  "Application's HTTP API" should {
    "ask questions" in {
      IO.race(
        Main.run(Nil).flatMap { code => IO(fail(s"application terminated : $code")) },
        for {
          _ <- IO.sleep(5.seconds)
          response <- IO.delay {
            requests.get(
              url = "http://localhost:8080/api/questions/random?number=2",
              readTimeout = 1.minutes.toMillis.toInt,
              connectTimeout = 1.minutes.toMillis.toInt
            )
          }
          _ = println(response.text())
          _ = println(decode[List[Question]](response.text()))
          questions = decode[List[Question]](response.text()).getOrElse(Nil)
        } yield {
          questions.size shouldBe 2
          questions.foreach(_ shouldBe a[Question])
        }
      ).unsafeRunSync()
    }
  }
}
