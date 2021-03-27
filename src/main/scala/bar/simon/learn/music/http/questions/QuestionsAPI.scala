package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import sttp.model.StatusCode._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{endpoint, statusCode, _}

trait QuestionsAPI extends QuestionsCodecs with QuestionErrorTapirCodecs {

  import QuestionsAPIExamples._

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(askRandomQuestionEndpoint)

  def askRandomQuestionEndpoint: Endpoint[Option[Int], NegativeNumberOfQuestions, List[Question], Any] =
    endpoint
      .get
      .name("Ask random questions")
      .description("Ask random music questions.")
      .in("api" / "questions" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(scaleQuestions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )
}
