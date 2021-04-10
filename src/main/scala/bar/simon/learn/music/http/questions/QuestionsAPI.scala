package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{
  IntervalBetweenNotes,
  ScaleFormula,
  ScaleHarmonization,
  ScaleNotes
}
import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import sttp.model.StatusCode._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{endpoint, statusCode, _}

trait QuestionsAPI extends QuestionsCodecs with QuestionErrorTapirCodecs {

  import QuestionsAPIExamples._

  type QuestionEndpoint[Q] = Endpoint[Option[Int], NegativeNumberOfQuestions, List[Q], Any]

  val endpoints: List[Endpoint[_, _, _, _]] =
    List(
      randomAnyQuestionEndpoint,
      randomScaleQuestionEndpoint,
      randomIntervalQuestionEndpoint,
      scaleNotesQuestionEndpoint,
      scaleFormulaQuestionEndpoint,
      scaleHarmonizationQuestionEndpoint,
      intervalBetweenNotesQuestionEndpoint
    )

  def randomAnyQuestionEndpoint: QuestionEndpoint[Question] =
    endpoint.get
      .name("Random questions")
      .description("Ask random music questions.")
      .in("random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(questions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def randomScaleQuestionEndpoint: QuestionEndpoint[Question] =
    endpoint.get
      .name("Random scale questions")
      .description("Ask random scale questions.")
      .in("scale" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(scaleQuestions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def randomIntervalQuestionEndpoint: QuestionEndpoint[Question] =
    endpoint.get
      .name("Random scale questions")
      .description("Ask random interval questions.")
      .in("interval" / "random")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[Question]].example(intervalQuestions))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def intervalBetweenNotesQuestionEndpoint: QuestionEndpoint[IntervalBetweenNotes] =
    endpoint.get
      .name("'Interval between notes' questions")
      .description("Ask 'interval between notes' questions.")
      .in("interval" / "betweenNotes")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[IntervalBetweenNotes]].example(intervalBetweenNotes))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def scaleNotesQuestionEndpoint: QuestionEndpoint[ScaleNotes] =
    endpoint.get
      .name("'Scale notes' questions")
      .description("Ask 'scale notes' questions.")
      .in("scale" / "notes")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[ScaleNotes]].example(scaleNotes))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def scaleFormulaQuestionEndpoint: QuestionEndpoint[ScaleFormula] =
    endpoint.get
      .name("'Scale formula' questions")
      .description("Ask 'scale formula' questions.")
      .in("scale" / "formula")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[ScaleFormula]].example(scaleFormula))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )

  def scaleHarmonizationQuestionEndpoint: QuestionEndpoint[ScaleHarmonization] =
    endpoint.get
      .name("'Scale harmonization' questions")
      .description("Ask 'scale harmonization' questions.")
      .in("scale" / "harmonization")
      .in(query[Option[Int]](name = "number"))
      .out(statusCode(Ok))
      .out(jsonBody[List[ScaleHarmonization]].example(scaleHarmonization))
      .errorOut(
        oneOf(
          statusMapping(BadRequest, anyJsonBody[NegativeNumberOfQuestions].example(negativeNumberOfQuestions))
        )
      )
}
