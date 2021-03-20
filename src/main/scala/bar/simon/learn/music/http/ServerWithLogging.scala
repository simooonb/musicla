package bar.simon.learn.music.http

import com.typesafe.scalalogging.StrictLogging
import org.http4s.HttpRoutes
import zio.RIO
import zio.logging.Logging
import zio.interop.catz.taskConcurrentInstance
import org.http4s.server.middleware.{Logger => MiddlewareLogger}

object ServerWithLogging extends StrictLogging {

  implicit final class ServerWithLogging(routes: HttpRoutes[RIO[Logging, *]]) {

    def withRequestAndResponseLogging: HttpRoutes[RIO[Logging, *]] =
      MiddlewareLogger.httpRoutes(logHeaders = true, logBody = true)(routes)
  }

}
