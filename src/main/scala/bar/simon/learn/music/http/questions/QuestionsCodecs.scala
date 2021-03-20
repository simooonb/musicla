package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{
  IntervalBetweenNotes,
  ScaleFormula,
  ScaleHarmonization,
  ScaleNotes
}
import io.circe._
import io.circe.generic.extras.defaults._
import io.circe.generic.extras.semiauto._
import io.circe.refined._
import io.circe.syntax._

trait QuestionsCodecs {

  implicit val questionCodec: Codec[Question]                         = deriveEnumerationCodec
  implicit val scaleNotesCodec: Codec[ScaleNotes]                     = deriveConfiguredCodec
  implicit val scaleFormulaCodec: Codec[ScaleFormula]                 = deriveConfiguredCodec
  implicit val scaleHarmonizationCodec: Codec[ScaleHarmonization]     = deriveConfiguredCodec
  implicit val intervalBetweenNotesCodec: Codec[IntervalBetweenNotes] = deriveConfiguredCodec

}
