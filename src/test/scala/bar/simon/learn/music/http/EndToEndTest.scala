package bar.simon.learn.music.http

import bar.simon.learn.music.Main
import bar.simon.learn.music.domain.Sample
import bar.simon.learn.music.domain.answers.{Answer, AnswerVerification}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{
  IntervalBetweenNotes,
  ScaleFormula,
  ScaleHarmonization,
  ScaleNotes
}
import bar.simon.learn.music.http.answers.AnswerVerificationCodecs
import cats.effect.{CancelToken, ContextShift, IO, Timer}
import io.circe.parser.decode
import io.circe.{Decoder, Encoder}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class EndToEndTest extends AnyWordSpec with Matchers with BeforeAndAfterAll with AnswerVerificationCodecs {

  import Sample._

  implicit val timer: Timer[IO]     = IO.timer(ExecutionContext.global)
  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  private val log = LoggerFactory.getLogger(getClass)

  private var app: CancelToken[IO] = _

  override def beforeAll(): Unit = {
    super.beforeAll()
    app = Main.run(Nil).unsafeRunCancelable { termination =>
      log.info(s"Application terminated with: $termination")
      termination.left.foreach(error => log.error(error.getMessage, error))
    }
  }

  override protected def afterAll(): Unit = {
    log.info(s"Stopping Application")
    app.unsafeRunSync()
    ()
  }

  // TODO: test wrong json
  "Application's HTTP API" should {
    "ask questions" in askQuestions.unsafeRunSync()
    "give answers" in giveAnswers.unsafeRunSync()
    "verify answers" in verifyAnswers.unsafeRunSync()
  }

  def askQuestions: IO[Unit] =
    for {
      _    <- IO.sleep(2.seconds)
      rq   <- get[Question]("http://localhost:8080/api/questions/random?number=2")
      rsq  <- get[Question]("http://localhost:8080/api/questions/scale/random?number=2")
      riq  <- get[Question]("http://localhost:8080/api/questions/interval/random?number=2")
      sfq  <- get[ScaleFormula]("http://localhost:8080/api/questions/scale/formula?number=2")
      snq  <- get[ScaleNotes]("http://localhost:8080/api/questions/scale/notes?number=2")
      shq  <- get[ScaleHarmonization]("http://localhost:8080/api/questions/scale/harmonization?number=2")
      ibnq <- get[IntervalBetweenNotes]("http://localhost:8080/api/questions/interval/betweenNotes?number=2")
      all = List(rq, rsq, riq, sfq, snq, shq, ibnq)
    } yield all.foreach(_.size shouldBe 2)

  def giveAnswers: IO[Unit] =
    (for {
      _        <- IO.sleep(2.seconds)
      interval <- post[Question, Answer]("http://localhost:8080/api/answers", notesIntervalQuestion)
      //          chords   <- post[Question, Answer]("http://localhost:8080/api/answers", scaleHarmonizationQuestion)
      notes   <- post[Question, Answer]("http://localhost:8080/api/answers", scaleNotesQuestion)
      formula <- post[Question, Answer]("http://localhost:8080/api/answers", scaleFormulaQuestion)
    } yield {
      // TODO: add back when octave are serialized/deserialized
      //  the test fails because the octave is lost when the answer is serialized/deserialized
      //          chords shouldBe Some(scaleHarmonizationAnswer)
      interval shouldBe notesIntervalAnswer
      notes shouldBe scaleNotesAnswer
      formula shouldBe scaleFormulaAnswer
    }).void

  def verifyAnswers: IO[Unit] =
    (for {
      _ <- IO.sleep(2.seconds)
      goodNotes <- post[AnswerVerification, Boolean](
        "http://localhost:8080/api/answers/verify",
        goodScaleNoteVerification
      )
      goodInterval <- post[AnswerVerification, Boolean](
        "http://localhost:8080/api/answers/verify",
        goodNotesIntervalVerification
      )
      wrongInterval <- post[AnswerVerification, Boolean](
        "http://localhost:8080/api/answers/verify",
        wrongNotesIntervalVerification
      )
      wrongFormula <- post[AnswerVerification, Boolean](
        "http://localhost:8080/api/answers/verify",
        wrongScaleFormulaVerification
      )
    } yield {
      goodNotes shouldBe true
      goodInterval shouldBe true
      wrongInterval shouldBe false
      wrongFormula shouldBe false
    }).void

  private def get[Q](url: String)(implicit d: Decoder[List[Q]]): IO[List[Q]] =
    IO {
      requests.get(
        url = url,
        readTimeout = 1.minutes.toMillis.toInt,
        connectTimeout = 1.minutes.toMillis.toInt
      )
    }.map(response => decode[List[Q]](response.text()).getOrElse(Nil))

  private def post[Q, A](url: String, input: Q)(implicit e: Encoder[Q], d: Decoder[A]): IO[A] =
    IO {
      requests.post(
        url = url,
        data = e.apply(input).spaces4SortKeys,
        readTimeout = 1.minutes.toMillis.toInt,
        connectTimeout = 1.minutes.toMillis.toInt
      )
    }.map(response => decode[A](response.text()).toOption.get)
}
