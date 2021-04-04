package bar.simon.learn.music.domain.music

import bar.simon.learn.music.domain.music.Alteration._

final case class Note(
    name: NoteName,
    alteration: Option[Alteration] = None,
    octave: Int = 1
) {
  private val semiToneOctave: Int = (octave - 1) * 12

  val noteIndex: Int     = name.noteIndex + (octave - 1) * 7
  val semiToneValue: Int = name.semiToneValue + alteration.map(_.semiToneValue).getOrElse(0) + semiToneOctave
  val label: String      = s"$name${alteration.map(_.label).getOrElse("")}"

  def add(interval: Interval): Note = {
    val nextNameIndex   = interval.naturalNoteOffset + NoteName.all.indexOf(name)
    val nextOctave      = if (nextNameIndex >= NoteName.all.size) octave + 1 else octave
    val nextOctaveValue = (nextOctave - 1) * 12
    val nextName        = NoteName.all(nextNameIndex % NoteName.all.size)
    val alterationLeft  = interval.semiToneLength - (nextName.semiToneValue + nextOctaveValue - semiToneValue)

    alterationLeft match {
      case 2  => Note(nextName, Some(SharpSharp), octave = nextOctave)
      case 1  => Note(nextName, Some(Sharp), octave = nextOctave)
      case -1 => Note(nextName, Some(Flat), octave = nextOctave)
      case -2 => Note(nextName, Some(FlatFlat), octave = nextOctave)
      case _  => Note(nextName, octave = nextOctave)
    }
  }

}
