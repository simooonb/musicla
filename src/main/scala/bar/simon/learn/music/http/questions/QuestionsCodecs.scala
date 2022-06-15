package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.{Note, Scale}
import bar.simon.learn.music.domain.questions.{Question, QuestionId}
import bar.simon.learn.music.domain.questions.Question._
import bar.simon.learn.music.http.serialization.MusicCodecs
import io.circe._
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success}

trait QuestionsCodecs extends MusicCodecs {

  case class QuestionMapping(id: QuestionId, `type`: String, scale: Option[Scale], notes: Option[List[Note]] = None)

  implicit val questionIdCodec: Codec[QuestionId] = deriveUnwrappedCodec[QuestionId]

  implicit val questionDecoder: Decoder[Question] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping(_, "ScaleNotes", Some(scale), None)                => Success(ScaleNotes(scale))
    case QuestionMapping(_, "ScaleFormula", Some(scale), None)              => Success(ScaleFormula(scale))
    case QuestionMapping(_, "ScaleHarmonization", Some(scale), None)        => Success(ScaleHarmonization(scale))
    case QuestionMapping(_, "IntervalBetweenNotes", None, Some(List(l, r))) => Success(IntervalBetweenNotes(l, r))
    case other                                                              => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val questionEncoder: Encoder[Question] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap {
        case q @ ScaleNotes(scale)          => QuestionMapping(q.id, "ScaleNotes", Some(scale), None)
        case q @ ScaleFormula(scale)        => QuestionMapping(q.id, "ScaleFormula", Some(scale), None)
        case q @ ScaleHarmonization(scale)  => QuestionMapping(q.id, "ScaleHarmonization", Some(scale), None)
        case q @ IntervalBetweenNotes(l, r) => QuestionMapping(q.id, "IntervalBetweenNotes", None, Some(List(l, r)))
      }

  implicit val scaleFormulaDecoder: Decoder[ScaleFormula] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping(_, "ScaleFormula", Some(scale), None) => Success(ScaleFormula(scale))
    case other                                                 => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleFormulaEncoder: Encoder[ScaleFormula] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case q @ ScaleFormula(scale) => QuestionMapping(q.id, "ScaleFormula", Some(scale), None) }

  implicit val scaleNotesDecoder: Decoder[ScaleNotes] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping(_, "ScaleNotes", Some(scale), None) => Success(ScaleNotes(scale))
    case other                                               => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleNotesEncoder: Encoder[ScaleNotes] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case q @ ScaleNotes(scale) => QuestionMapping(q.id, "ScaleNotes", Some(scale), None) }

  implicit val scaleHarmonizationDecoder: Decoder[ScaleHarmonization] =
    deriveConfiguredDecoder[QuestionMapping].emapTry {
      case QuestionMapping(_, "ScaleHarmonization", Some(scale), None) => Success(ScaleHarmonization(scale))
      case other                                                       => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val scaleHarmonizationEncoder: Encoder[ScaleHarmonization] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case q @ ScaleHarmonization(scale) =>
        QuestionMapping(q.id, "ScaleHarmonization", Some(scale), None)
      }

  implicit val intervalBetweenNotesDecoder: Decoder[IntervalBetweenNotes] =
    deriveConfiguredDecoder[QuestionMapping].emapTry {
      case QuestionMapping(_, "IntervalBetweenNotes", None, Some(List(l, r))) => Success(IntervalBetweenNotes(l, r))
      case other                                                              => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val intervalBetweenNotesEncoder: Encoder[IntervalBetweenNotes] =
    deriveConfiguredEncoder[QuestionMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case q @ IntervalBetweenNotes(l, r) =>
        QuestionMapping(q.id, "IntervalBetweenNotes", None, Some(List(l, r)))
      }
}
