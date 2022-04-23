package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.usecase.{GetAnswerUseCase, VerifyAnswerUseCase}
import bar.simon.learn.music.http.Controller
import cats.effect.{Concurrent, ContextShift, Sync, Timer}
import cats.implicits._
import org.http4s.HttpRoutes
import sttp.tapir.server.http4s.Http4sServerInterpreter.toRouteRecoverErrors

final class AnswersController[F[_]](
    getAnswerUseCase: GetAnswerUseCase,
    verifyAnswerUseCase: VerifyAnswerUseCase
)(implicit
    timer: Timer[F],
    cs: ContextShift[F],
    F: Sync[F],
    C: Concurrent[F]
) extends Controller[F]
    with AnswersAPI {

  override def routes: HttpRoutes[F] =
    getAnswerRoute <+> verifyAnswerRoute

  def getAnswerRoute: HttpRoutes[F] =
    toRouteRecoverErrors(getAnswerEndpoint) { question =>
      F.delay(getAnswerUseCase.from(question))
    }

  def verifyAnswerRoute: HttpRoutes[F] =
    toRouteRecoverErrors(verifyAnswerEndpoint) { verification =>
      F.delay(verifyAnswerUseCase.verify(verification))
    }
}
