package bar.simon.learn.music.domain.music

import Chord.{Minor => CMinor, Major => CMajor, Diminished => CDiminished}

sealed trait Scale {
  def root: Note
  def formula: String
  def name: String
  def harmonization: List[Note => Chord]

  lazy val intervals: List[Interval] = formula.split(" ").toList.flatMap(Interval.get)

  lazy val notes: List[Note] = intervals.map(root.add)

  lazy val harmonized: List[Chord] = (notes zip harmonization).map { case (note, chord) => chord(note) }
}

object Scale {
  final case class Chromatic(root: Note) extends Scale {
    val formula       = "1 b2 2 b3 3 4 b5 5 b6 6 b7 7"
    val name          = "Chromatic"
    val harmonization = List.empty[Note => Chord]
  }

  final case class MajorPentatonic(root: Note) extends Scale {
    val formula       = "1 3 4 5 7"
    val name          = "MajorPentatonic"
    val harmonization = List.empty[Note => Chord]
  }

  final case class MinorPentatonic(root: Note) extends Scale {
    val formula       = "1 b3 4 5 b7"
    val name          = "MinorPentatonic"
    val harmonization = List.empty[Note => Chord]
  }

  final case class Major(root: Note) extends Scale {
    val formula       = "1 2 3 4 5 6 7"
    val name          = "Major"
    val harmonization = List(CMajor, CMinor, CMinor, CMajor, CMajor, CMinor, CDiminished)
  }

  final case class Minor(root: Note) extends Scale {
    val formula       = "1 2 b3 4 5 b6 b7"
    val name          = "Minor"
    val harmonization = List(CMinor, CDiminished, CMajor, CMinor, CMinor, CMajor, CMajor)
  }
}
