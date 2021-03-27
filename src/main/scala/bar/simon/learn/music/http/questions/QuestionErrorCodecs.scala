package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import bar.simon.learn.music.http.serialization.ErrorCodecs.buildErrorResponse
import io.circe.Encoder

trait QuestionErrorCodecs {

  implicit final val negativeNumberOfQuestionsEncoder: Encoder[NegativeNumberOfQuestions] =
    err => buildErrorResponse(err.getName, err.getMessage)

}
