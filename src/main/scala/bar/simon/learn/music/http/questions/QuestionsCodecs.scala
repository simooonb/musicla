package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.{Alteration, Note, NoteName, Scale}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question.{IntervalBetweenNotes, ScaleFormula, ScaleHarmonization, ScaleNotes}
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.semiauto._

import scala.util.{Failure, Success}

trait QuestionsCodecs {

  case class NoteNameMapping(name: String)
  case class AlterationMapping(name: String)
  case class QuestionMapping(`type`: String, scale: Option[Scale], notes: List[Note] = Nil)

  implicit val customConfig: Configuration =
    Configuration.default.withDiscriminator("type")

  implicit val noteCodec: Codec[Note]                                 = deriveCodec
  implicit val scaleCodec: Codec[Scale]                               = deriveCodec
  implicit val scaleNotesCodec: Codec[ScaleNotes]                     = deriveCodec
  implicit val scaleFormulaCodec: Codec[ScaleFormula]                 = deriveCodec
  implicit val scaleHarmonizationCodec: Codec[ScaleHarmonization]     = deriveCodec
  implicit val intervalBetweenNotesCodec: Codec[IntervalBetweenNotes] = deriveCodec

  implicit val noteNameDecoder: Decoder[NoteName] = deriveDecoder[NoteNameMapping].emapTry {
    case NoteNameMapping("A") | NoteNameMapping("a") => Success(NoteName.A)
    case NoteNameMapping("B") | NoteNameMapping("b") => Success(NoteName.A)
    case NoteNameMapping("C") | NoteNameMapping("c") => Success(NoteName.A)
    case NoteNameMapping("D") | NoteNameMapping("d") => Success(NoteName.A)
    case NoteNameMapping("E") | NoteNameMapping("e") => Success(NoteName.A)
    case NoteNameMapping("F") | NoteNameMapping("f") => Success(NoteName.A)
    case NoteNameMapping("G") | NoteNameMapping("g") => Success(NoteName.A)
    case other                                       => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val noteNameEncoder: Encoder[NoteName] = deriveEncoder[NoteNameMapping].contramap {
    case NoteName.A => NoteNameMapping("A")
    case NoteName.B => NoteNameMapping("B")
    case NoteName.C => NoteNameMapping("C")
    case NoteName.D => NoteNameMapping("D")
    case NoteName.E => NoteNameMapping("E")
    case NoteName.F => NoteNameMapping("F")
    case NoteName.G => NoteNameMapping("G")
  }

  implicit val alterationDecoder: Decoder[Alteration] = deriveDecoder[AlterationMapping].emapTry {
    case AlterationMapping("Sharp") | AlterationMapping("#")       => Success(Alteration.Sharp)
    case AlterationMapping("SharpSharp") | AlterationMapping("##") => Success(Alteration.SharpSharp)
    case AlterationMapping("Flat") | AlterationMapping("b")        => Success(Alteration.Flat)
    case AlterationMapping("FlatFlat") | AlterationMapping("bb")   => Success(Alteration.FlatFlat)
    case other                                                     => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val alterationEncoder: Encoder[Alteration] = deriveEncoder[AlterationMapping].contramap {
    case Alteration.Sharp      => AlterationMapping("Sharp")
    case Alteration.SharpSharp => AlterationMapping("SharpSharp")
    case Alteration.Flat       => AlterationMapping("Flat")
    case Alteration.FlatFlat   => AlterationMapping("FlatFlat")
  }

  implicit val questionDecoder: Decoder[Question] = deriveDecoder[QuestionMapping].emapTry {
    case QuestionMapping("ScaleNotes", Some(scale), Nil)         => Success(Question.ScaleNotes(scale))
    case QuestionMapping("ScaleFormula", Some(scale), Nil)       => Success(Question.ScaleFormula(scale))
    case QuestionMapping("ScaleHarmonization", Some(scale), Nil) => Success(Question.ScaleHarmonization(scale))
    case QuestionMapping("IntervalBetweenNotes", None, List(left, right)) =>
      Success(Question.IntervalBetweenNotes(left, right))
    case other => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val questionEncoder: Encoder[Question] = deriveEncoder[QuestionMapping].contramap {
    case Question.ScaleNotes(scale)                 => QuestionMapping("ScaleNotes", Some(scale))
    case Question.ScaleFormula(scale)               => QuestionMapping("ScaleFormula", Some(scale))
    case Question.ScaleHarmonization(scale)         => QuestionMapping("ScaleHarmonization", Some(scale))
    case Question.IntervalBetweenNotes(left, right) => QuestionMapping("IntervalBetweenNotes", None, List(left, right))
  }
}
