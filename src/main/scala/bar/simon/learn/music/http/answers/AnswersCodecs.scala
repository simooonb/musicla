package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.Answer
import bar.simon.learn.music.domain.answers.Answer.{
  IntervalBetweenNotesAnswer,
  ScaleFormulaAnswer,
  ScaleHarmonizationAnswer,
  ScaleNotesAnswer
}
import bar.simon.learn.music.domain.music._
import bar.simon.learn.music.http.serialization.MusicCodecs
import io.circe._
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success}

trait AnswersCodecs extends MusicCodecs {

  case class AnswerMapping(
      `type`: String,
      notes: Option[List[Note]] = None,
      intervals: Option[List[Interval]] = None,
      chords: Option[List[Chord]] = None,
      interval: Option[Option[Interval]] = None
  )

  implicit val answerDecoder: Decoder[Answer] = deriveConfiguredDecoder[AnswerMapping].emapTry {
    case AnswerMapping("ScaleNotes", Some(n), None, None, None)           => Success(ScaleNotesAnswer(n))
    case AnswerMapping("ScaleFormula", None, Some(is), None, None)        => Success(ScaleFormulaAnswer(is))
    case AnswerMapping("ScaleHarmonization", None, None, Some(c), None)   => Success(ScaleHarmonizationAnswer(c))
    case AnswerMapping("IntervalBetweenNotes", None, None, None, Some(i)) => Success(IntervalBetweenNotesAnswer(i))
    case other                                                            => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val answerEncoder: Encoder[Answer] =
    deriveConfiguredEncoder[AnswerMapping]
      .mapJson(_.deepDropNullValues)
      .contramap {
        case ScaleNotesAnswer(notes)              => AnswerMapping("ScaleNotes", notes = Some(notes))
        case ScaleFormulaAnswer(intervals)        => AnswerMapping("ScaleFormula", intervals = Some(intervals))
        case ScaleHarmonizationAnswer(chords)     => AnswerMapping("ScaleHarmonization", chords = Some(chords))
        case IntervalBetweenNotesAnswer(interval) => AnswerMapping("IntervalBetweenNotes", interval = Some(interval))
      }

  implicit val scaleFormulaAnswerDecoder: Decoder[ScaleFormulaAnswer] = deriveConfiguredDecoder[AnswerMapping].emapTry {
    case AnswerMapping("ScaleFormula", None, Some(intervals), None, None) => Success(ScaleFormulaAnswer(intervals))
    case other                                                            => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleFormulaAnswerEncoder: Encoder[ScaleFormulaAnswer] =
    deriveConfiguredEncoder[AnswerMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleFormulaAnswer(intervals) => AnswerMapping("ScaleFormula", intervals = Some(intervals)) }

  implicit val scaleNotesAnswerDecoder: Decoder[ScaleNotesAnswer] = deriveConfiguredDecoder[AnswerMapping].emapTry {
    case AnswerMapping("ScaleNotes", Some(notes), None, None, None) => Success(ScaleNotesAnswer(notes))
    case other                                                      => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val scaleNotesAnswerEncoder: Encoder[ScaleNotesAnswer] =
    deriveConfiguredEncoder[AnswerMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleNotesAnswer(notes) => AnswerMapping("ScaleNotes", notes = Some(notes)) }

  implicit val scaleHarmonizationAnswerDecoder: Decoder[ScaleHarmonizationAnswer] =
    deriveConfiguredDecoder[AnswerMapping].emapTry {
      case AnswerMapping("ScaleHarmonization", None, None, Some(c), None) => Success(ScaleHarmonizationAnswer(c))
      case other                                                          => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val scaleHarmonizationAnswerEncoder: Encoder[ScaleHarmonizationAnswer] =
    deriveConfiguredEncoder[AnswerMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case ScaleHarmonizationAnswer(chords) => AnswerMapping("ScaleHarmonization", chords = Some(chords)) }

  implicit val intervalBetweenNotesAnswerDecoder: Decoder[IntervalBetweenNotesAnswer] =
    deriveConfiguredDecoder[AnswerMapping].emapTry {
      case AnswerMapping("IntervalBetweenNotes", None, None, None, Some(i)) => Success(IntervalBetweenNotesAnswer(i))
      case other                                                            => Failure(DecodingFailure(s"failed to decode $other", Nil))
    }

  implicit val intervalBetweenNotesAnswerEncoder: Encoder[IntervalBetweenNotesAnswer] =
    deriveConfiguredEncoder[AnswerMapping]
      .mapJson(_.deepDropNullValues)
      .contramap { case IntervalBetweenNotesAnswer(i) => AnswerMapping("IntervalBetweenNotes", interval = Some(i)) }
}
