package bar.simon.learn.music.usecase

import bar.simon.learn.music.domain.questions.{Question, QuestionError}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed
import zio.IO

final class AskQuestionUseCase(parameters: Gen.Parameters, initialSeed: Seed) {

  var seed: Seed = initialSeed

  def askRandom(numberOfQuestions: Int): IO[QuestionError, List[Question]] =
    IO {
      seed = seed.next
      Gen
        .listOfN(numberOfQuestions, Question.randomQuestion)
        .pureApply(parameters, seed)
    }
      .mapError(QuestionError.UnknownError)
}
