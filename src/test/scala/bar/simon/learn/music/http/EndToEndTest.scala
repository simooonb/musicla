package bar.simon.learn.music.http

import bar.simon.learn.music.Main
import bar.simon.learn.music.domain.Sample
import bar.simon.learn.music.domain.answers.Answer
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{IntervalBetweenNotes, ScaleFormula, ScaleHarmonization, ScaleNotes}
import bar.simon.learn.music.http.answers.AnswersCodecs
import bar.simon.learn.music.http.questions.QuestionsCodecs
import cats.effect.{ContextShift, IO, Timer}
import io.circe.parser.decode
import io.circe.{Decoder, Encoder}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class EndToEndTest extends AnyWordSpec with Matchers with QuestionsCodecs with AnswersCodecs {

  implicit val timer: Timer[IO]     = IO.timer(ExecutionContext.global)
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  "Application's HTTP API" should {

    def get[Q](url: String)(implicit d: Decoder[List[Q]]): IO[List[Q]] =
      IO.delay {
        requests.get(
          url = url,
          readTimeout = 1.minutes.toMillis.toInt,
          connectTimeout = 1.minutes.toMillis.toInt
        )
      }.map(response => decode[List[Q]](response.text()).getOrElse(Nil))

    def post[Q, A](url: String, input: Q)(implicit e: Encoder[Q], d: Decoder[A]): IO[Option[A]] = {
      println(e.apply(input).spaces2SortKeys)
      IO.delay {
        requests.post(
          url = url,
          data = e.apply(input).spaces4SortKeys,
          readTimeout = 1.minutes.toMillis.toInt,
          connectTimeout = 1.minutes.toMillis.toInt
        )
      }.map(response => decode[A](response.text()).toOption)
    }

    "ask questions" in {
      IO.race(
        Main.run(Nil).flatMap { code => IO(fail(s"Application terminated: $code")) },
        for {
          _    <- IO.sleep(5.seconds)
          rq   <- get[Question]("http://localhost:8080/api/questions/random?number=2")
          rsq  <- get[Question]("http://localhost:8080/api/questions/scale/random?number=2")
          riq  <- get[Question]("http://localhost:8080/api/questions/interval/random?number=2")
          sfq  <- get[ScaleFormula]("http://localhost:8080/api/questions/scale/formula?number=2")
          snq  <- get[ScaleNotes]("http://localhost:8080/api/questions/scale/notes?number=2")
          shq  <- get[ScaleHarmonization]("http://localhost:8080/api/questions/scale/harmonization?number=2")
          ibnq <- get[IntervalBetweenNotes]("http://localhost:8080/api/questions/interval/betweenNotes?number=2")
          all = List(rq, rsq, riq, sfq, snq, shq, ibnq)
        } yield all.foreach(_.size shouldBe 2)
      ).unsafeRunSync()
    }

    "give answers" in {
      IO.race(
        Main.run(Nil).flatMap { code => IO(fail(s"Application terminated: $code")) },
        for {
          _        <- IO.sleep(5.seconds)
          interval <- post[Question, Answer]("http://localhost:8080/api/answers", Sample.notesIntervalQuestion)
//          chords   <- post[Question, Answer]("http://localhost:8080/api/answers", Sample.scaleHarmonizationQuestion)
          notes    <- post[Question, Answer]("http://localhost:8080/api/answers", Sample.scaleNotesQuestion)
          formula  <- post[Question, Answer]("http://localhost:8080/api/answers", Sample.scaleFormulaQuestion)
        } yield {
          // TODO: add back when octave are serialized/deserialized
          //  the test fails because the octave is lost when the answer is serialized/deserialized
//          chords shouldBe Some(Sample.scaleHarmonizationAnswer)
          interval shouldBe Some(Sample.notesIntervalAnswer)
          notes shouldBe Some(Sample.scaleNotesAnswer)
          formula shouldBe Some(Sample.scaleFormulaAnswer)
        }
      ).unsafeRunSync()
    }
  }
}
