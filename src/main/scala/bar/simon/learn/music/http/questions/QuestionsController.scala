package bar.simon.learn.music.http.questions

import bar.simon.learn.music.http.Controller
import bar.simon.learn.music.usecase.AskQuestionUseCase
import org.http4s.HttpRoutes
import zio.RIO
import zio.logging.{Logging, log}

final class QuestionsController(askQuestionUseCase: AskQuestionUseCase) extends Controller with QuestionsAPI {

  override def routes: HttpRoutes[RIO[Logging, *]] =
    askRandomQuestionRoute

  private def askRandomQuestionRoute: HttpRoutes[RIO[Logging, *]] =
    askRandomQuestionEndpoint.toRoutes { _ =>
      askQuestionUseCase.askRandom(1).map(_.head).tapBoth(
        error =>
          log.error(
            s"Tried to ask a random question but error ${error.getClass.getSimpleName} occurred."
          ),
        _ => log.info(s"Asked a random question.")
      )
    }

}
