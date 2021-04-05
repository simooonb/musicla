package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.http.serialization.ErrorCodecs.buildErrorResponse
import io.circe.Encoder

trait AnswerErrorCodecs {

  implicit final val inputQuestionErrorEncoder: Encoder[InputQuestionError] =
    err => buildErrorResponse(err.getName, err.getMessage)

}
