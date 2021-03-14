package bar.simon.learn.music.usecase

import bar.simon.learn.music.domain.questions.Question
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

final class AskQuestionUseCase(parameters: Gen.Parameters, initialSeed: Seed) {

  var seed: Seed = initialSeed

  def askRandom(numberOfQuestions: Int): List[Question] = {
    seed = seed.next
    Gen
      .listOfN(numberOfQuestions, Question.randomQuestion)
      .pureApply(parameters, seed)
  }
}
