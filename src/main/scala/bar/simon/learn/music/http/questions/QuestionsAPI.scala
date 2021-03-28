package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import sttp.model.StatusCode._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{endpoint, statusCode, _}

trait QuestionsAPI extends QuestionsCodecs with QuestionErrorTapirCodecs {

  import QuestionsAPIExamples._

  type QuestionEndpoint = Endpoint[Option[Int], NegativeNumberOfQuestions, List[Question], Any]

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(randomAnyQuestionEndpoint, randomScaleQuestionEndpoint, randomIntervalQuestionEndpoint)

  def randomAnyQuestionEndpoint: QuestionEndpoint =
    endpoint
      .get
      .name("Random questions")
      .description("Ask random music questions.")
      .in("api" / "questions" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(questions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def randomScaleQuestionEndpoint: QuestionEndpoint =
    endpoint
      .get
      .name("Random scale questions")
      .description("Ask random scale questions.")
      .in("api" / "questions" / "scale" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(scaleQuestions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def randomIntervalQuestionEndpoint: QuestionEndpoint =
    endpoint
      .get
      .name("Random scale questions")
      .description("Ask random interval questions.")
      .in("api" / "questions" / "interval" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(intervalQuestions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )
}
