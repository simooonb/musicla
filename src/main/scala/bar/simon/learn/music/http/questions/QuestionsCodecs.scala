package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question._
import bar.simon.learn.music.http.serialization.MusicCodecs
import io.circe._
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success}

trait QuestionsCodecs extends MusicCodecs {

  case class QuestionMapping(`type`: String, scale: Option[Scale], notes: Option[List[Note]] = None)

  implicit val questionDecoder: Decoder[Question] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping("ScaleNotes", Some(scale), None)                => Success(ScaleNotes(scale))
    case QuestionMapping("ScaleFormula", Some(scale), None)              => Success(ScaleFormula(scale))
    case QuestionMapping("ScaleHarmonization", Some(scale), None)        => Success(ScaleHarmonization(scale))
    case QuestionMapping("IntervalBetweenNotes", None, Some(List(l, r))) => Success(IntervalBetweenNotes(l, r))
    case other                                                           => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val questionEncoder: Encoder[Question] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap {
        case ScaleNotes(scale)          => QuestionMapping("ScaleNotes", Some(scale), None)
        case ScaleFormula(scale)        => QuestionMapping("ScaleFormula", Some(scale), None)
        case ScaleHarmonization(scale)  => QuestionMapping("ScaleHarmonization", Some(scale), None)
        case IntervalBetweenNotes(l, r) => QuestionMapping("IntervalBetweenNotes", None, Some(List(l, r)))
      }

  implicit val scaleFormulaDecoder: Decoder[ScaleFormula] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping("ScaleFormula", Some(scale), None) => Success(ScaleFormula(scale))
    case other                                              => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleFormulaEncoder: Encoder[ScaleFormula] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleFormula(scale) => QuestionMapping("ScaleFormula", Some(scale), None) }

  implicit val scaleNotesDecoder: Decoder[ScaleNotes] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping("ScaleNotes", Some(scale), None) => Success(ScaleNotes(scale))
    case other                                            => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleNotesEncoder: Encoder[ScaleNotes] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleNotes(scale) => QuestionMapping("ScaleNotes", Some(scale), None) }

  implicit val scaleHarmonizationDecoder: Decoder[ScaleHarmonization] =
    deriveConfiguredDecoder[QuestionMapping].emapTry {
      case QuestionMapping("ScaleHarmonization", Some(scale), None) => Success(ScaleHarmonization(scale))
      case other                                                    => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val scaleHarmonizationEncoder: Encoder[ScaleHarmonization] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleHarmonization(scale) => QuestionMapping("ScaleHarmonization", Some(scale), None) }

  implicit val intervalBetweenNotesDecoder: Decoder[IntervalBetweenNotes] =
    deriveConfiguredDecoder[QuestionMapping].emapTry {
      case QuestionMapping("IntervalBetweenNotes", None, Some(List(l, r))) => Success(IntervalBetweenNotes(l, r))
      case other                                                           => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val intervalBetweenNotesEncoder: Encoder[IntervalBetweenNotes] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case IntervalBetweenNotes(l, r) => QuestionMapping("IntervalBetweenNotes", None, Some(List(l, r))) }
}
