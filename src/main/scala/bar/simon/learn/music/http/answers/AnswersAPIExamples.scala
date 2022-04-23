package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.Answer.ScaleNotesAnswer
import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.domain.answers.AnswerVerification
import bar.simon.learn.music.domain.music.NoteName.C
import bar.simon.learn.music.domain.music.{MusicError, Note, Scale}
import bar.simon.learn.music.domain.questions.Question._

object AnswersAPIExamples {

  val scaleNotesQuestion: ScaleNotes         = ScaleNotes(Scale.Major(Note(C)))
  val scaleNotesAnswer: ScaleNotesAnswer     = scaleNotesQuestion.answer
  val answerVerification: AnswerVerification = AnswerVerification(scaleNotesQuestion, scaleNotesAnswer)
  val inputQuestionError: InputQuestionError = InputQuestionError()
  val musicError: MusicError                 = InputQuestionError()

}
