package bar.simon.learn.music.http

import bar.simon.learn.music.Main
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{
  IntervalBetweenNotes,
  ScaleFormula,
  ScaleHarmonization,
  ScaleNotes
}
import bar.simon.learn.music.http.questions.QuestionsCodecs
import cats.effect.{ContextShift, IO, Timer}
import io.circe.Decoder
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import io.circe.parser.decode

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class EndToEndTest extends AnyWordSpec with Matchers with QuestionsCodecs {

  implicit val timer: Timer[IO]     = IO.timer(ExecutionContext.global)
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  "Application's HTTP API" should {

    def callEndpoint[Q](url: String)(implicit d: Decoder[List[Q]]): IO[List[Q]] =
      IO.delay {
        requests.get(
          url = url,
          readTimeout = 1.minutes.toMillis.toInt,
          connectTimeout = 1.minutes.toMillis.toInt
        )
      }.map(response => decode[List[Q]](response.text()).getOrElse(Nil))

    "ask questions" in {
      IO.race(
        Main.run(Nil).flatMap { code => IO(fail(s"Application terminated: $code")) },
        for {
          _    <- IO.sleep(5.seconds)
          rq   <- callEndpoint[Question]("http://localhost:8080/api/questions/random?number=2")
          rsq  <- callEndpoint[Question]("http://localhost:8080/api/questions/scale/random?number=2")
          riq  <- callEndpoint[Question]("http://localhost:8080/api/questions/interval/random?number=2")
          sfq  <- callEndpoint[ScaleFormula]("http://localhost:8080/api/questions/scale/formula?number=2")
          snq  <- callEndpoint[ScaleNotes]("http://localhost:8080/api/questions/scale/notes?number=2")
          shq  <- callEndpoint[ScaleHarmonization]("http://localhost:8080/api/questions/scale/harmonization?number=2")
          ibnq <- callEndpoint[IntervalBetweenNotes]("http://localhost:8080/api/questions/interval/betweenNotes?number=2")
          all = List(rq, rsq, riq, sfq, snq, shq, ibnq)
        } yield all.foreach(_.size shouldBe 2)
      ).unsafeRunSync()
    }
  }
}
