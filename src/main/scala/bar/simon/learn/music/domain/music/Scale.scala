package bar.simon.learn.music.domain.music

import org.scalacheck.Gen

final case class Scale(root: Note, formula: String, name: String) {

  lazy val intervals: List[Interval] =
    formula.split(" ").toList.flatMap(Interval.get)

  lazy val notes: List[Note] =
    intervals.map(root.add)

  lazy val harmonized: List[Chord] = {
    val chords =
      intervals.indices.toList.map(root => List(0, 2, 4).map(offset => intervals((root + offset) % intervals.size)))
    chords.map(Chord.fromIntervals(root, _))
  }

}

object Scale {
  def random: Gen[Scale] =
    for {
      note  <- Note.random
      scale <- Gen.oneOf(List(major _, minor _))
    } yield scale(note)

  def chromatic(note: Note): Scale = Scale(note, "1 b2 2 b3 3 4 b5 5 b6 6 b7 7", "chromatic")

  def major(note: Note): Scale = Scale(note, "1 2 3 4 5 6 7", "major")
  def minor(note: Note): Scale = Scale(note, "1 2 b3 4 5 b6 b7", "minor")

  def majorPentatonic(note: Note): Scale = Scale(note, "1 3 4 5 7", "major pentatonic")
  def minorPentatonic(note: Note): Scale = Scale(note, "1 b3 4 5 b7", "minor pentatonic")
}
