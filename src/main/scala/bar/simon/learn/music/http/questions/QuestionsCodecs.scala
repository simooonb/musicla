package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.{Alteration, Chord, Note, NoteName, Scale}
import bar.simon.learn.music.domain.questions.Question
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success}

trait QuestionsCodecs {

  case class NoteNameMapping(name: String)
  case class AlterationMapping(name: String)
  case class QuestionMapping(`type`: String, scale: Option[Scale], notes: Option[List[Note]] = None)

  implicit val customConfig: Configuration =
    Configuration.default.withDiscriminator("type")

  implicit val noteCodec: Codec[Note]   = deriveConfiguredCodec
  implicit val chordCodec: Codec[Chord] = deriveConfiguredCodec
  implicit val scaleCodec: Codec[Scale] = deriveConfiguredCodec

  implicit val noteNameDecoder: Decoder[NoteName] = deriveUnwrappedDecoder[NoteNameMapping].emapTry {
    case NoteNameMapping("A") | NoteNameMapping("a") => Success(NoteName.A)
    case NoteNameMapping("B") | NoteNameMapping("b") => Success(NoteName.A)
    case NoteNameMapping("C") | NoteNameMapping("c") => Success(NoteName.A)
    case NoteNameMapping("D") | NoteNameMapping("d") => Success(NoteName.A)
    case NoteNameMapping("E") | NoteNameMapping("e") => Success(NoteName.A)
    case NoteNameMapping("F") | NoteNameMapping("f") => Success(NoteName.A)
    case NoteNameMapping("G") | NoteNameMapping("g") => Success(NoteName.A)
    case other                                       => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val noteNameEncoder: Encoder[NoteName] = deriveUnwrappedEncoder[NoteNameMapping].contramap {
    case NoteName.A => NoteNameMapping("A")
    case NoteName.B => NoteNameMapping("B")
    case NoteName.C => NoteNameMapping("C")
    case NoteName.D => NoteNameMapping("D")
    case NoteName.E => NoteNameMapping("E")
    case NoteName.F => NoteNameMapping("F")
    case NoteName.G => NoteNameMapping("G")
  }

  implicit val alterationDecoder: Decoder[Alteration] = deriveUnwrappedDecoder[AlterationMapping].emapTry {
    case AlterationMapping("Sharp") | AlterationMapping("#")       => Success(Alteration.Sharp)
    case AlterationMapping("SharpSharp") | AlterationMapping("##") => Success(Alteration.SharpSharp)
    case AlterationMapping("Flat") | AlterationMapping("b")        => Success(Alteration.Flat)
    case AlterationMapping("FlatFlat") | AlterationMapping("bb")   => Success(Alteration.FlatFlat)
    case other                                                     => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val alterationEncoder: Encoder[Alteration] = deriveUnwrappedEncoder[AlterationMapping].contramap {
    case Alteration.Sharp      => AlterationMapping("Sharp")
    case Alteration.SharpSharp => AlterationMapping("SharpSharp")
    case Alteration.Flat       => AlterationMapping("Flat")
    case Alteration.FlatFlat   => AlterationMapping("FlatFlat")
  }

  implicit val questionDecoder: Decoder[Question] = deriveConfiguredDecoder[QuestionMapping].emapTry {
    case QuestionMapping("ScaleNotes", Some(scale), None)         => Success(Question.ScaleNotes(scale))
    case QuestionMapping("ScaleFormula", Some(scale), None)       => Success(Question.ScaleFormula(scale))
    case QuestionMapping("ScaleHarmonization", Some(scale), None) => Success(Question.ScaleHarmonization(scale))
    case QuestionMapping("IntervalBetweenNotes", None, Some(List(left, right))) =>
      Success(Question.IntervalBetweenNotes(left, right))
    case other => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val questionEncoder: Encoder[Question] = deriveConfiguredEncoder[QuestionMapping].contramap {
    case Question.ScaleNotes(scale)         => QuestionMapping("ScaleNotes", Some(scale))
    case Question.ScaleFormula(scale)       => QuestionMapping("ScaleFormula", Some(scale))
    case Question.ScaleHarmonization(scale) => QuestionMapping("ScaleHarmonization", Some(scale))
    case Question.IntervalBetweenNotes(left, right) =>
      QuestionMapping("IntervalBetweenNotes", None, Some(List(left, right)))
  }
}
