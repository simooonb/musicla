package bar.simon.learn.music.http.questions

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
    randomAnyQuestionRoute <+>
      randomScaleQuestionRoute <+>
      randomIntervalQuestionRoute <+>
      scaleNotesQuestionRoute <+>
      scaleFormulaQuestionRoute <+>
      scaleHarmonizationQuestionRoute <+>
      intervalBetweenNotesQuestionRoute

  private def randomAnyQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomAnyQuestionEndpoint, askQuestionUseCase.anyRandom)

  private def randomScaleQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomScaleQuestionEndpoint, askQuestionUseCase.anyScale)

  private def randomIntervalQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(randomIntervalQuestionEndpoint, askQuestionUseCase.anyInterval)

  private def scaleNotesQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(scaleNotesQuestionEndpoint, askQuestionUseCase.askScaleNotes)

  private def scaleFormulaQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(scaleFormulaQuestionEndpoint, askQuestionUseCase.askScaleFormula)

  private def scaleHarmonizationQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(scaleHarmonizationQuestionEndpoint, askQuestionUseCase.askScaleHarmonization)

  private def intervalBetweenNotesQuestionRoute: HttpRoutes[F] =
    randomQuestionRoute(intervalBetweenNotesQuestionEndpoint, askQuestionUseCase.askIntervalBetweenNotes)

  private def randomQuestionRoute[Q](endpoint: QuestionEndpoint[Q], questions: Int => List[Q]): HttpRoutes[F] =
    toRouteRecoverErrors(endpoint) {
      case Some(count) if count > 0 => F.delay(questions(count))
      case None                     => F.delay(questions(1))
      case _                        => F.raiseError(NegativeNumberOfQuestions())
    }

}
