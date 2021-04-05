package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.domain.music.NoteName.C
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.Question._

object AnswersAPIExamples {

  val scaleNotesAnswer: String = ScaleNotes(Scale.major(Note(C))).answer

  val inputQuestionError: InputQuestionError = InputQuestionError()

}
