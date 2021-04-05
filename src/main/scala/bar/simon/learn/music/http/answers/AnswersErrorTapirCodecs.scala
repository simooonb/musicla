package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerError.InputQuestionError
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

trait AnswersErrorTapirCodecs {

  implicit val inputQuestionErrorTapirCodec: JsonCodec[InputQuestionError] =
    circeCodec[InputQuestionError]

}
