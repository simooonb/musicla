package bar.simon.learn.music.domain.usecase

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Generators._
import bar.simon.learn.music.domain.questions.Question.{IntervalBetweenNotes, ScaleFormula, ScaleHarmonization, ScaleNotes}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import org.slf4j.{Logger, LoggerFactory}

final class AskQuestionUseCase(parameters: Gen.Parameters, initialSeed: Seed) {

  var seed: Seed  = initialSeed
  val log: Logger = LoggerFactory.getLogger(getClass)

  def anyRandom(numberOfQuestions: Int): List[Question] = {
    log.debug(s"$numberOfQuestions random questions asked.")
    generateQuestions(numberOfQuestions, anyQuestion)
  }

  def anyScale(numberOfQuestions: Int): List[Question] = {
    log.debug(s"$numberOfQuestions scale questions asked.")
    generateQuestions(numberOfQuestions, scaleQuestion)
  }

  def anyInterval(numberOfQuestions: Int): List[Question] = {
    log.debug(s"$numberOfQuestions interval questions asked.")
    generateQuestions(numberOfQuestions, intervalQuestion)
  }

  def askIntervalBetweenNotes(numberOfQuestions: Int): List[IntervalBetweenNotes] = {
    log.debug(s"$numberOfQuestions 'interval between notes' questions asked.")
    generateQuestions(numberOfQuestions, intervalBetweenNotes)
  }

  def askScaleNotes(numberOfQuestions: Int): List[ScaleNotes] = {
    log.debug(s"$numberOfQuestions 'scale notes' questions asked.")
    generateQuestions(numberOfQuestions, scaleNotes)
  }

  def askScaleFormula(numberOfQuestions: Int): List[ScaleFormula] = {
    log.debug(s"$numberOfQuestions 'scale formula' questions asked.")
    generateQuestions(numberOfQuestions, scaleFormula)
  }

  def askScaleHarmonization(numberOfQuestions: Int): List[ScaleHarmonization] = {
    log.debug(s"$numberOfQuestions 'scale harmonization' questions asked.")
    generateQuestions(numberOfQuestions, scaleHarmonization)
  }

  private def generateQuestions[Q](numberOfQuestions: Int, questionGen: Gen[Q]): List[Q] = {
    seed = seed.next
    Gen.listOfN(numberOfQuestions, questionGen).pureApply(parameters, seed)
  }
}
