package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.http.questions.QuestionsCodecs
import sttp.model.StatusCode._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{endpoint, statusCode, _}

trait AnswersAPI extends QuestionsCodecs with AnswersErrorTapirCodecs {

  import AnswersAPIExamples._

  type AnswerEndpoint = Endpoint[Question, InputQuestionError, String, Any]

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(trueAnswerEndpoint)

  def trueAnswerEndpoint: AnswerEndpoint =
    endpoint.post
      .name("Correct answer")
      .description("Retrieve the correct answer from a question.")
      .in("api" / "answers" / "correct")
      .in(jsonBody[Question])
      .out(statusCode(Ok))
      .out(jsonBody[String].example(scaleNotesAnswer))
      .errorOut(
        oneOf(statusMapping(BadRequest, anyJsonBody[InputQuestionError].example(inputQuestionError)))
      )
}
