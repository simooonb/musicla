package bar.simon.learn.music.domain.answers

import scala.util.control.NoStackTrace

sealed trait AnswerError extends Exception with Product with Serializable with NoStackTrace {
  def getName: String = getClass.getCanonicalName
}

object AnswerError {

  final case class InputQuestionError() extends Exception("Cannot serialize body as question.") with AnswerError

  final case class UnknownError(cause: Throwable) extends Exception(cause) with AnswerError

}
