package bar.simon.learn.music.domain.music

case class Chord(root: Note, formula: String, name: String) {
  lazy val label: String = notes.map(_.label).mkString(" ")

  lazy val intervals: List[Interval] =
    formula.split(" ").toList.flatMap(Interval.get)

  lazy val notes: List[Note] =
    intervals.map(root.add)

  // TODO: add methods to transform chords (e.g. from major to major7)
}

object Chord {
  def fromIntervals(note: Note, intervals: List[Interval]): Chord =
    Chord(note, intervals.map(_.label).mkString(" "), "")

  def major(note: Note): Chord = Chord(note, "1 3 5", "major")
  def major7(note: Note): Chord = Chord(note, "1 3 5 7", "major7")
  def major9(note: Note): Chord = Chord(note, "1 3 5 7 9", "major9")
  def major11(note: Note): Chord = Chord(note, "1 3 5 7 11", "major11")
  def major13(note: Note): Chord = Chord(note, "1 3 5 7 13", "major13")

  def minor(note: Note): Chord = Chord(note, "1 b3 5", "minor")
  def minor7(note: Note): Chord = Chord(note, "1 b3 5 b7", "minor7")
  def minor9(note: Note): Chord = Chord(note, "1 b3 5 b7 9", "minor9")
  def minor11(note: Note): Chord = Chord(note, "1 b3 5 b7 11", "minor11")
  def minor13(note: Note): Chord = Chord(note, "1 b3 5 b7 13", "minor13")

  def dominant7(note: Note): Chord = Chord(note, "1 3 5 b7", "dominant")
  def dominant9(note: Note): Chord = Chord(note, "1 3 5 b7 9", "dominant9")
  def dominant11(note: Note): Chord = Chord(note, "1 3 5 b7 11", "dominant11")
  def dominant13(note: Note): Chord = Chord(note, "1 3 5 b7 13", "dominant13")

  def diminished(note: Note): Chord = Chord(note, "1 b3 b5", "diminished")
  def diminished7(note: Note): Chord = Chord(note, "1 b3 b5 b7", "diminished7")

  def power(note: Note): Chord = Chord(note, "1 5", "power")

  def add2(note: Note): Chord = Chord(note, "1 2 3 5", "add2")
  def add4(note: Note): Chord = Chord(note, "1 3 4 5", "add4")
  def sus2(note: Note): Chord = Chord(note, "1 2 5", "sus2")
  def sus4(note: Note): Chord = Chord(note, "1 4 5", "sus4")
}
