package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.domain.answers.{Answer, AnswerVerification}
import bar.simon.learn.music.domain.questions.Question
import sttp.model.StatusCode._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{endpoint, statusCode, _}

trait AnswersAPI extends AnswerVerificationCodecs with AnswersErrorTapirCodecs {

  import AnswersAPIExamples._

  type AnswerEndpoint       = Endpoint[Question, InputQuestionError, Answer, Any]
  type VerificationEndpoint = Endpoint[AnswerVerification, InputQuestionError, Boolean, Any]

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(getAnswerEndpoint, verifyAnswerEndpoint)

  def getAnswerEndpoint: AnswerEndpoint =
    endpoint.post
      .name("Get answer")
      .description("Retrieve the correct answer from a question.")
      .in(jsonBody[Question].example(scaleNotesQuestion))
      .out(statusCode(Ok))
      .out(jsonBody[Answer].example(scaleNotesAnswer))
      .errorOut(
        oneOf(statusMapping(BadRequest, anyJsonBody[InputQuestionError].example(inputQuestionError)))
      )

  def verifyAnswerEndpoint: VerificationEndpoint =
    endpoint.post
      .name("Verify answer")
      .description("Verify a given answer for a question.")
      .in(jsonBody[AnswerVerification].example(answerVerification))
      .out(statusCode(Ok))
      .out(jsonBody[Boolean].example(true))
      .errorOut( // fixme: should be AnswerError + QuestionError
        oneOf(statusMapping(BadRequest, anyJsonBody[InputQuestionError].example(inputQuestionError)))
      )
}
