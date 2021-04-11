package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.Answer.ScaleNotesAnswer
import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.domain.music.NoteName.C
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.Question._

object AnswersAPIExamples {

  val scaleNotesAnswer: ScaleNotesAnswer = ScaleNotes(Scale.Major(Note(C))).answer

  val inputQuestionError: InputQuestionError = InputQuestionError()

}
