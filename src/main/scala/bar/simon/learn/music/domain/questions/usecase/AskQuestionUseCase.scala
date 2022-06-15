package bar.simon.learn.music.domain.questions.usecase

import bar.simon.learn.music.domain.questions.Generators._
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{
  IntervalBetweenNotes,
  ScaleFormula,
  ScaleHarmonization,
  ScaleNotes
}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.slf4j.{Logger, LoggerFactory}

final class AskQuestionUseCase(parameters: Gen.Parameters, initialSeed: Seed) {

  var seed: Seed  = initialSeed
  val log: Logger = LoggerFactory.getLogger(getClass)

  def anyRandom(numberOfQuestions: Int): List[Question] =
    generateQuestions(numberOfQuestions, anyQuestion)

  def anyScale(numberOfQuestions: Int): List[Question] =
    generateQuestions(numberOfQuestions, scaleQuestion)

  def askIntervalBetweenNotes(numberOfQuestions: Int): List[IntervalBetweenNotes] =
    generateQuestions(numberOfQuestions, intervalBetweenNotes)

  def askScaleNotes(numberOfQuestions: Int): List[ScaleNotes] =
    generateQuestions(numberOfQuestions, scaleNotes)

  def askScaleFormula(numberOfQuestions: Int): List[ScaleFormula] =
    generateQuestions(numberOfQuestions, scaleFormula)

  def askScaleHarmonization(numberOfQuestions: Int): List[ScaleHarmonization] =
    generateQuestions(numberOfQuestions, scaleHarmonization)

  private def generateQuestions[Q](numberOfQuestions: Int, questionGen: Gen[Q]): List[Q] = {
    seed = seed.next
    // using set to generate a distinct list of questions
    Gen.containerOfN[Set, Q](numberOfQuestions, questionGen).pureApply(parameters, seed).toList
  }
}
