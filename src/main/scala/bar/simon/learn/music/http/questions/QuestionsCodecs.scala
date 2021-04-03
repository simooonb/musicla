package bar.simon.learn.music.http.questions

import bar.simon.learn.music.domain.music.{Alteration, Chord, Note, NoteName, Scale}
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.domain.questions.Question._
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success, Try}

trait QuestionsCodecs {

  case class NoteNameMapping(name: String)
  case class NoteMapping(name: String)
  case class ChordMapping(chord: String)
  case class AlterationMapping(name: String)
  case class QuestionMapping(`type`: String, scale: Option[Scale], notes: Option[List[Note]] = None)

  implicit val customConfig: Configuration =
    Configuration.default.withDiscriminator("type")

  implicit val noteEncoder: Encoder[Note] = deriveUnwrappedEncoder[NoteMapping].contramap { note =>
    NoteMapping(note.label)
  }

  implicit val noteDecoder: Decoder[Note] = deriveUnwrappedDecoder[NoteMapping].emapTry { case NoteMapping(note) =>
    val maybeNote =
      for {
        name            <- note.headOption.map(_.toString)
        noteName        <- NoteName.all.collectFirst { case nn if name == nn.toString => nn }
        maybeAlteration <- Alteration.byLabelWithNone.get(note.tail)
      } yield Note(noteName, maybeAlteration)

    Try(maybeNote.get)
  }

  implicit val scaleCodec: Codec[Scale] = deriveConfiguredCodec

  implicit val chordEncoder: Encoder[Chord] = deriveUnwrappedEncoder[ChordMapping].contramap { chord =>
    ChordMapping(s"${chord.root.label} ${chord.name}")
  }

  implicit val chordDecoder: Decoder[Chord] = deriveConfiguredDecoder

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
    alteration => AlterationMapping(alteration.label)
  }

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
}
