package bar.simon.learn.music.domain.questions

import bar.simon.learn.music.domain.music.{Interval, Note, Scale}

import org.scalacheck.Gen

sealed trait Question {
  val answer: String
}

object Question {

  val scaleQuestions: List[Scale => Question]           = List(ScaleNotes, ScaleFormula, ScaleHarmonization)
  val intervalQuestions: List[(Note, Note) => Question] = List(IntervalBetweenNotes)
  val totalNumberOfQuestions: Int                       = scaleQuestions.size + intervalQuestions.size

  final case class ScaleNotes(scale: Scale) extends Question {
    val answer: String = scale.notes.map(_.label).mkString(" ")
  }

  final case class ScaleFormula(scale: Scale) extends Question {
    val answer: String = scale.formula
  }

  final case class ScaleHarmonization(scale: Scale) extends Question {
    val answer: String = scale.harmonized.map(_.label).mkString(" ")
  }

  final case class IntervalBetweenNotes(left: Note, right: Note) extends Question {
    val answer: String = Interval.between(left, right).map(_.label).getOrElse("")
  }
}
