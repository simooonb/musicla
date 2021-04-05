package bar.simon.learn.music.http

import bar.simon.learn.music.http.questions.QuestionsController
import bar.simon.learn.music.domain.usecase.{AskQuestionUseCase, GetAnswerUseCase}
import bar.simon.learn.music.http.answers.AnswersController
import cats.effect._
import cats.implicits._
import net.logstash.logback.marker.Markers.append
import org.http4s.HttpApp
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.jetty._
import org.http4s.server.{Router, Server}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.slf4j.LoggerFactory

final class ServerBuilder[F[_]](implicit
    timer: Timer[F],
    cs: ContextShift[F],
    F: Sync[F],
    ce: ConcurrentEffect[F]
) {
  private val logger = LoggerFactory.getLogger(getClass)

  def resource: Resource[F, Server[F]] =
    for {
      serverConfiguration <- ServerConfiguration.load
      _      = logger.info(append("configuration", "http"), serverConfiguration.show)
      router = createRouter()
      server <- createServer(serverConfiguration, router)
    } yield server

  private def createRouter(): HttpApp[F] = {
    val initialSeed         = Seed.random()
    val generatorParameters = Gen.Parameters.default

    val askQuestionUseCase  = new AskQuestionUseCase(generatorParameters, initialSeed)
    val getAnswerUseCase = new GetAnswerUseCase()

    val questionsController = new QuestionsController(askQuestionUseCase)
    val answerController = new AnswersController(getAnswerUseCase)

    Router(
      "/" -> questionsController.routes ++ answerController.routes
    ).orNotFound
  }

  private def createServer(
      configuration: ServerConfiguration,
      router: HttpApp[F]
  ): Resource[F, Server[F]] =
    JettyBuilder[F]
      .bindHttp(configuration.port.value, configuration.host.value)
      .mountHttpApp(router, "")
      .resource
}
