package bar.simon.learn.music.usecase

import bar.simon.learn.music.domain.questions.Question
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

final class AskQuestionUseCase(parameters: Gen.Parameters, initialSeed: Seed) {

  var seed: Seed = initialSeed

  def anyRandom(numberOfQuestions: Int): List[Question] =
    any(numberOfQuestions, Question.randomQuestion)

  def anyScale(numberOfQuestions: Int): List[Question] =
    any(numberOfQuestions, Question.randomScaleQuestion)

  def anyInterval(numberOfQuestions: Int): List[Question] =
    any(numberOfQuestions, Question.randomIntervalQuestion)

  private def any(numberOfQuestions: Int, questionGen: Gen[Question]): List[Question] = {
    seed = seed.next
    Gen
      .listOfN(numberOfQuestions, questionGen)
      .pureApply(parameters, seed)
  }
}
