package bar.simon.learn.music.domain.usecase

import bar.simon.learn.music.domain.questions.Question

final class GetAnswerUseCase() {

  def from(question: Question): String =
    question.answer

}
