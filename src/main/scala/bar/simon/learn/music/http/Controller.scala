package bar.simon.learn.music.http

import org.http4s.HttpRoutes

trait Controller[F[_]] {

  def routes: HttpRoutes[F]

}
