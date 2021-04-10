package bar.simon.learn.music.domain.questions

import bar.simon.learn.music.domain.answers.Answer
import bar.simon.learn.music.domain.answers.Answer.{IntervalBetweenNotesAnswer, ScaleFormulaAnswer, ScaleHarmonizationAnswer, ScaleNotesAnswer}
import bar.simon.learn.music.domain.music.{Interval, Note, Scale}

sealed trait Question {
  val answer: Answer
}

object Question {

  val scaleQuestions: List[Scale => Question]           = List(ScaleNotes, ScaleFormula, ScaleHarmonization)
  val intervalQuestions: List[(Note, Note) => Question] = List(IntervalBetweenNotes)
  val totalNumberOfQuestions: Int                       = scaleQuestions.size + intervalQuestions.size

  final case class ScaleNotes(scale: Scale) extends Question {
    val answer: ScaleNotesAnswer = ScaleNotesAnswer(scale.notes)
  }

  final case class ScaleFormula(scale: Scale) extends Question {
    val answer: ScaleFormulaAnswer = ScaleFormulaAnswer(scale.intervals)
  }

  final case class ScaleHarmonization(scale: Scale) extends Question {
    val answer: ScaleHarmonizationAnswer = ScaleHarmonizationAnswer(scale.harmonized)
  }

  final case class IntervalBetweenNotes(left: Note, right: Note) extends Question {
    val answer: IntervalBetweenNotesAnswer = IntervalBetweenNotesAnswer(Interval.between(left, right))
  }
}
