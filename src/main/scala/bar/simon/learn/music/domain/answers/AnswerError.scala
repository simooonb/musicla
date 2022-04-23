package bar.simon.learn.music.domain.answers

import bar.simon.learn.music.domain.music.MusicError

sealed trait AnswerError extends MusicError {
  def getName: String = getClass.getCanonicalName
}

object AnswerError {

  final case class InputQuestionError() extends Exception("Cannot serialize body as question.") with AnswerError

  final case class UnknownError(cause: Throwable) extends Exception(cause) with AnswerError

}
