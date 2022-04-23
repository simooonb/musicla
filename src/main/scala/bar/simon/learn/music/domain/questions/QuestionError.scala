package bar.simon.learn.music.domain.questions

import bar.simon.learn.music.domain.music.MusicError

sealed trait QuestionError extends MusicError {
  def getName: String = getClass.getCanonicalName
}

object QuestionError {

  final case class NegativeNumberOfQuestions()
      extends Exception("Cannot ask for a negative number of questions")
      with QuestionError

  final case class UnknownError(cause: Throwable) extends Exception(cause) with QuestionError

}
