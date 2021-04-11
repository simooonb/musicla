package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.NoteName.{C, F}
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question._
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions

object QuestionsAPIExamples {

  val scaleNotes: List[ScaleNotes]                 = List(ScaleNotes(Scale.Major(Note(C))))
  val scaleFormula: List[ScaleFormula]             = List(ScaleFormula(Scale.Major(Note(C))))
  val scaleHarmonization: List[ScaleHarmonization] = List(ScaleHarmonization(Scale.Major(Note(C))))
  val scaleQuestions: List[Question]               = scaleNotes ++ scaleFormula ++ scaleHarmonization

  val intervalBetweenNotes: List[IntervalBetweenNotes] = List(IntervalBetweenNotes(Note(C), Note(F)))
  val intervalQuestions: List[Question]                = intervalBetweenNotes

  val questions: List[Question] = scaleQuestions ++ intervalQuestions

  val negativeNumberOfQuestions: NegativeNumberOfQuestions = NegativeNumberOfQuestions()

}
