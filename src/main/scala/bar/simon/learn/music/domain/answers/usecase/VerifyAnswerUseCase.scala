package bar.simon.learn.music.domain.answers.usecase

import bar.simon.learn.music.domain.answers.AnswerVerification

final class VerifyAnswerUseCase() {

  def verify(verification: AnswerVerification): Boolean =
    verification.question.answer == verification.answer

}
