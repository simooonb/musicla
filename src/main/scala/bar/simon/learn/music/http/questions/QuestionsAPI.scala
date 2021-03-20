package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import sttp.model.StatusCode._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.generic.auto._
import sttp.tapir.{endpoint, statusCode, _}

trait QuestionsAPI extends QuestionsCodecs {

  import QuestionsAPIExamples._

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(askRandomQuestionEndpoint)

  def askRandomQuestionEndpoint =
    endpoint
      .summary("Ask a random music question.")
      .get
      .in("api" / "questions" / "random")
      .out(statusCode(Ok))
      .out(jsonBody[Question].example(scaleQuestion))
}
