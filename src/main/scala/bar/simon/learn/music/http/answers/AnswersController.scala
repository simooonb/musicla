package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.usecase.GetAnswerUseCase
import bar.simon.learn.music.http.Controller
import cats.effect.{Concurrent, ContextShift, Sync, Timer}
import org.http4s.HttpRoutes
import sttp.tapir.server.http4s.Http4sServerInterpreter.toRouteRecoverErrors

final class AnswersController[F[_]](getAnswerUseCase: GetAnswerUseCase)(implicit
    timer: Timer[F],
    cs: ContextShift[F],
    F: Sync[F],
    C: Concurrent[F]
) extends Controller[F]
    with AnswersAPI {

  override def routes: HttpRoutes[F] =
    trueAnswerRoute

  private def trueAnswerRoute: HttpRoutes[F] =
    toRouteRecoverErrors(trueAnswerEndpoint) { question =>
      F.delay(getAnswerUseCase.from(question))
    }
}
