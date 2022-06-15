package bar.simon.learn.music.domain.questions

import bar.simon.learn.music.domain.music.Alteration.{Flat, Sharp}
import bar.simon.learn.music.domain.music.Scale.{Major, Minor}
import bar.simon.learn.music.domain.music.{Note, NoteName, Scale}
import bar.simon.learn.music.domain.questions.Question._
import org.scalacheck.Gen

object Generators {

  def note: Gen[Note] =
    for {
      name       <- Gen.oneOf(NoteName.all)
      alteration <- Gen.oneOf(List(Some(Sharp), Some(Flat), None))
    } yield Note(name, alteration)

  def scale: Gen[Scale] =
    for {
      note  <- note
      scale <- Gen.oneOf(List(Major, Minor))
    } yield scale(note)

  def scaleQuestion: Gen[Question] =
    for {
      question <- Gen.oneOf(scaleQuestions)
      scale    <- scale
    } yield question(scale)

  def anyQuestion: Gen[Question] =
    for {
      askScale <- Gen.prob(0.5)
      question <- if (askScale) scaleQuestion else intervalBetweenNotes
    } yield question

  def intervalBetweenNotes: Gen[IntervalBetweenNotes] =
    for {
      left  <- note
      right <- note
      if left.label != right.label
      intervalQuestion = IntervalBetweenNotes(left, right)
      if intervalQuestion.answer.interval.nonEmpty
    } yield intervalQuestion

  def scaleNotes: Gen[ScaleNotes] = scale.map(ScaleNotes)

  def scaleFormula: Gen[ScaleFormula] = scale.map(ScaleFormula)

  def scaleHarmonization: Gen[ScaleHarmonization] = scale.map(ScaleHarmonization)
}
