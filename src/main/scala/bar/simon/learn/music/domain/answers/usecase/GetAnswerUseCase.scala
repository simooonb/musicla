package bar.simon.learn.music.domain.answers.usecase

import bar.simon.learn.music.domain.answers.Answer
import bar.simon.learn.music.domain.questions.Question

final class GetAnswerUseCase() {

  def from(question: Question): Answer =
    question.answer

}
