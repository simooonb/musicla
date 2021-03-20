package bar.simon.learn.music.http

import bar.simon.learn.music.http.ServerWithLogging._
import bar.simon.learn.music.http.questions.QuestionsController
import bar.simon.learn.music.usecase.AskQuestionUseCase
import cats.implicits._
import net.logstash.logback.marker.Markers.append
import org.http4s.HttpApp
import org.http4s.server.jetty.JettyBuilder
import org.http4s.server.{Router, Server}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.slf4j.Logger
import zio.interop.catz._
import zio.logging.Logging
import zio.{RIO, RManaged, Runtime}

class ServerBuilder(logger: Logger) {
  def resource: RManaged[Logging, Server[RIO[Logging, *]]] =
    for {
      serverConfiguration <- ServerConfiguration.load.toManaged_
      _      = logger.info(append("configuration", "http"), serverConfiguration.show)
      router = createRouter()
      server <- createServer(serverConfiguration, router)
    } yield server

  private def createRouter(): HttpApp[RIO[Logging, *]] = {
    val initialSeed         = Seed.random()
    val generatorParameters = Gen.Parameters.default

    val askQuestionUseCase  = new AskQuestionUseCase(generatorParameters, initialSeed)
    val questionsController = new QuestionsController(askQuestionUseCase)

    Router(
      "/" -> questionsController.routes.withRequestAndResponseLogging
    ).orNotFound
  }

  private def createServer(
      configuration: ServerConfiguration,
      router: HttpApp[RIO[Logging, *]]
  ): RManaged[Logging, Server[RIO[Logging, *]]] =
    for {
      implicit0(runtime: Runtime[Logging]) <- RIO.runtime[Logging].toManaged_
      server <- JettyBuilder[RIO[Logging, *]]
        .bindHttp(configuration.port.value, configuration.host.value)
        .mountHttpApp(router, "")
        .resource
        .toManaged
    } yield server
}

object ServerBuilder {

  def apply(logger: Logger): ServerBuilder = new ServerBuilder(logger)

}
