package bar.simon.learn.music.http.serialization

import bar.simon.learn.music.domain.music.{Alteration, Chord, Interval, Note, NoteName, Scale}
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._

import scala.util.{Failure, Success, Try}

trait MusicCodecs {

  case class NoteNameMapping(name: String)
  case class NoteMapping(name: String)
  case class ChordMapping(chord: String)
  case class AlterationMapping(name: String)
  case class IntervalMapping(label: String)

  implicit val customConfig: Configuration = Configuration.default.withDiscriminator("type")

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
    ChordMapping(chord.label)
  }

  implicit val chordDecoder: Decoder[Chord] = deriveUnwrappedDecoder[ChordMapping].emapTry { chord =>
    val parts = chord.chord.split(" ")

    val maybeChord = for {
      note            <- parts.headOption
      noteName        <- note.headOption.flatMap(n => NoteName.all.collectFirst { case nn if n.toString == nn.toString => nn })
      maybeAlteration <- Alteration.byLabelWithNone.get(note.tail)
      chord           <- Chord.findWithName(Note(noteName, maybeAlteration), parts.last)
    } yield chord

    Try(maybeChord.get)
  }

  implicit val noteNameDecoder: Decoder[NoteName] = Decoder.decodeString.emapTry {
    case "A" | "a" => Success(NoteName.A)
    case "B" | "b" => Success(NoteName.B)
    case "C" | "c" => Success(NoteName.C)
    case "D" | "d" => Success(NoteName.D)
    case "E" | "e" => Success(NoteName.E)
    case "F" | "f" => Success(NoteName.F)
    case "G" | "g" => Success(NoteName.G)
    case other     => Failure(DecodingFailure(s"failed to decode $other", Nil))
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

  implicit val alterationDecoder: Decoder[Alteration] = Decoder.decodeString.emapTry {
    case "Sharp" | "#"       => Success(Alteration.Sharp)
    case "SharpSharp" | "##" => Success(Alteration.SharpSharp)
    case "Flat" | "b"        => Success(Alteration.Flat)
    case "FlatFlat" | "bb"   => Success(Alteration.FlatFlat)
    case other               => Failure(DecodingFailure(s"failed to decode $other", Nil))
  }

  implicit val alterationEncoder: Encoder[Alteration] = deriveUnwrappedEncoder[AlterationMapping].contramap {
    alteration => AlterationMapping(alteration.label)
  }

  implicit val intervalDecoder: Decoder[Interval] = deriveUnwrappedDecoder[IntervalMapping].emapTry { interval =>
    Try(Interval.get(interval.label).get)
  }

  implicit val intervalEncoder: Encoder[Interval] = deriveUnwrappedEncoder[IntervalMapping].contramap { interval =>
    IntervalMapping(interval.label)
  }

}
