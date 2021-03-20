package bar.simon.learn.music.domain.questions

import scala.util.control.NoStackTrace

sealed trait QuestionError extends RuntimeException with NoStackTrace with Product with Serializable

object QuestionError {

  final case class UnknownError(cause: Throwable) extends QuestionError

}
