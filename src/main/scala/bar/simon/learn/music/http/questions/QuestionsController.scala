package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import bar.simon.learn.music.http.Controller
import bar.simon.learn.music.usecase.AskQuestionUseCase
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
    askRandomQuestionRoute

  private def askRandomQuestionRoute: HttpRoutes[F] =
    toRouteRecoverErrors(askRandomQuestionEndpoint) {
      case Some(count) if count > 0 => F.delay(askQuestionUseCase.askRandom(count))
      case None                     => F.delay(askQuestionUseCase.askRandom(1))
      case _                        => F.raiseError(NegativeNumberOfQuestions())
    }

}
