package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.QuestionError.NegativeNumberOfQuestions
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

trait QuestionErrorTapirCodecs {

  implicit val negativeNumberOfQuestionsTapirCodec: JsonCodec[NegativeNumberOfQuestions] =
    circeCodec[NegativeNumberOfQuestions]

}
