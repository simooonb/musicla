package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.NoteName.{C, F}
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.{Question, QuestionError}
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions

object QuestionsAPIExamples {

  val scaleQuestions: List[Question.ScaleNotes]              = List(Question.ScaleNotes(Scale.major(Note(C))))
  val intervalQuestions: List[Question.IntervalBetweenNotes] = List(Question.IntervalBetweenNotes(Note(C), Note(F)))
  val questions: List[Question]                              = scaleQuestions ++ intervalQuestions

  val negativeNumberOfQuestions: QuestionError.NegativeNumberOfQuestions = NegativeNumberOfQuestions()

}
