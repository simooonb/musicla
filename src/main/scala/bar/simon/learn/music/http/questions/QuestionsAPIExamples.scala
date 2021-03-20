package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.NoteName.C
import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.Question

object QuestionsAPIExamples {

  val scaleQuestion: Question.ScaleNotes = Question.ScaleNotes(Scale.major(Note(C)))

}
