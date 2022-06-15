package bar.simon.learn.music.http

import bar.simon.learn.music.domain.answers.usecase.{GetAnswerUseCase, VerifyAnswerUseCase}
import bar.simon.learn.music.domain.questions.usecase.AskQuestionUseCase
import bar.simon.learn.music.http.answers.AnswersController
import bar.simon.learn.music.http.questions.QuestionsController
import cats.effect._
import cats.implicits._
import net.logstash.logback.marker.Markers.append
import org.http4s._
import org.http4s.implicits._
import org.http4s.server.jetty._
import org.http4s.server.middleware._
import org.http4s.server.{Router, Server}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

final class ServerBuilder[F[_]](implicit timer: Timer[F], cs: ContextShift[F], ce: ConcurrentEffect[F]) {
  private val logger = LoggerFactory.getLogger(getClass)

  def resource: Resource[F, Server[F]] =
    for {
      serverConfiguration <- ServerConfiguration.load
      _      = logger.info(append("configuration", "http"), serverConfiguration.show)
      router = buildRouter()
      server <- buildServer(serverConfiguration, router)
    } yield server

  private def buildRouter(): HttpApp[F] = {
    val initialSeed         = Seed.random()
    val generatorParameters = Gen.Parameters.default

    val askQuestionUseCase  = new AskQuestionUseCase(generatorParameters, initialSeed)
    val getAnswerUseCase    = new GetAnswerUseCase()
    val verifyAnswerUseCase = new VerifyAnswerUseCase()

    val questionsController = new QuestionsController(askQuestionUseCase)
    val answerController    = new AnswersController(getAnswerUseCase, verifyAnswerUseCase)

    val router = Router(
      "/api/questions"      -> questionsController.routes,
      "/api/answers"        -> answerController.getAnswerRoute,
      "/api/answers/verify" -> answerController.verifyAnswerRoute
    ).orNotFound

    val routerWithCors = CORS.policy.withAllowOriginAll // todo: change with url
//      .withAllowOriginHost(
//        Set(
//          Origin.Host(Uri.Scheme.https, Uri.RegName("localhost"), None),
//          Origin.Host(Uri.Scheme.http, Uri.RegName("http://localhost"), None)
//        )
//      )
      .withAllowCredentials(false)
      .withMaxAge(1.day)
      .apply(router)

    Logger.httpApp(
      logHeaders = true,
      logBody = true
    )(routerWithCors)
  }

  private def buildServer(
      configuration: ServerConfiguration,
      router: HttpApp[F]
  ): Resource[F, Server[F]] =
    JettyBuilder[F]
      .bindHttp(configuration.port.value, configuration.host.value)
      .mountHttpApp(router, "")
      .resource
}
