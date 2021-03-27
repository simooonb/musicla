package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.NoteName.C
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.{Question, QuestionError}
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions

object QuestionsAPIExamples {

  val scaleQuestions: List[Question.ScaleNotes] = List(Question.ScaleNotes(Scale.major(Note(C))))

  val negativeNumberOfQuestions: QuestionError.NegativeNumberOfQuestions = NegativeNumberOfQuestions()

}
