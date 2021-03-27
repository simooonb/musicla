package bar.simon.learn.music.domain.questions

import scala.util.control.NoStackTrace

sealed trait QuestionError extends Exception with Product with Serializable with NoStackTrace {
  def getName: String = getClass.getCanonicalName
}

object QuestionError {

  final case class NegativeNumberOfQuestions() extends Exception("test")
//      extends RuntimeException("Cannot ask for a negative number of questions.")
      with QuestionError

  final case class UnknownError(cause: Throwable) extends RuntimeException(cause) with QuestionError

}
