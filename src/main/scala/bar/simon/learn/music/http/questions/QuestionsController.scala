package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import bar.simon.learn.music.http.Controller
import bar.simon.learn.music.domain.usecase.AskQuestionUseCase
import cats.effect.{Concurrent, ContextShift, Sync, Timer}
import cats.implicits._
import org.http4s.HttpRoutes
import sttp.tapir.server.http4s.Http4sServerInterpreter.toRouteRecoverErrors

final class QuestionsController[F[_]](askQuestionUseCase: AskQuestionUseCase)(implicit
    timer: Timer[F],
    cs: ContextShift[F],
    F: Sync[F],
    C: Concurrent[F]
) extends Controller[F]
    with QuestionsAPI {

  override def routes: HttpRoutes[F] =
    randomAnyQuestionRoute <+> randomScaleQuestionRoute <+> randomIntervalQuestionRoute

  private def randomAnyQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomAnyQuestionEndpoint, askQuestionUseCase.anyRandom)

  private def randomScaleQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomScaleQuestionEndpoint, askQuestionUseCase.anyScale)

  private def randomIntervalQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomIntervalQuestionEndpoint, askQuestionUseCase.anyInterval)

  private def randomQuestionRoute(endpoint: QuestionEndpoint, questions: Int => List[Question]): HttpRoutes[F] =
    toRouteRecoverErrors(endpoint) {
      case Some(count) if count > 0 => F.delay(questions(count))
      case None                     => F.delay(questions(1))
      case _                        => F.raiseError(NegativeNumberOfQuestions())
    }

}
