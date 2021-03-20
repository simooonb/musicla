package bar.simon.learn.music.http

import org.http4s.HttpRoutes
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.ztapir.ZEndpoint
import zio.interop.catz.zioContextShift
import zio.{RIO, ZIO}
import zio.logging.Logging

trait Controller {

  def routes: HttpRoutes[RIO[Logging, *]]

  implicit class EndpointToRoutes[I, E, O](endpoint: ZEndpoint[I, E, O]) {

    def toRoutes[R](logic: I => ZIO[R, E, O]): HttpRoutes[RIO[R, *]] =
      ZHttp4sServerInterpreter.from(endpoint)(logic).toRoutes
  }

}
