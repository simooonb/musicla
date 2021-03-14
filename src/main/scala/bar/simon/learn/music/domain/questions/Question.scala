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

  def randomScaleQuestion: Gen[Question] =
    for {
      question <- Gen.oneOf(scaleQuestions)
      scale    <- Scale.random
    } yield question(scale)

  def randomIntervalQuestion: Gen[Question] =
    for {
      question <- Gen.oneOf(intervalQuestions)
      left     <- Note.random
      right    <- Note.random
    } yield question(left, right)

  def randomQuestion: Gen[Question] =
    for {
      scaleQuestion <- Gen.prob(0.5)
      question      <- if (scaleQuestion) randomScaleQuestion else randomIntervalQuestion
    } yield question

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
