package bar.simon.learn.music.domain.music

final case class Chord(root: Note, formula: String) {

  lazy val label: String = notes.map(_.label).mkString(" ")

  lazy val intervals: List[Interval] =
    formula.split(" ").toList.flatMap(Interval.get)

  lazy val notes: List[Note] =
    intervals.map(root.add)

  // TODO: add methods to transform chords (e.g. from major to major7)
}

object Chord {
  def fromIntervals(note: Note, intervals: List[Interval]): Chord =
    Chord(note, intervals.map(_.label).mkString(" "))

  def major(note: Note): Chord   = Chord(note, "1 3 5")
  def major7(note: Note): Chord  = Chord(note, "1 3 5 7")
  def major9(note: Note): Chord  = Chord(note, "1 3 5 7 9")
  def major11(note: Note): Chord = Chord(note, "1 3 5 7 11")
  def major13(note: Note): Chord = Chord(note, "1 3 5 7 13")

  def minor(note: Note): Chord   = Chord(note, "1 b3 5")
  def minor7(note: Note): Chord  = Chord(note, "1 b3 5 b7")
  def minor9(note: Note): Chord  = Chord(note, "1 b3 5 b7 9")
  def minor11(note: Note): Chord = Chord(note, "1 b3 5 b7 11")
  def minor13(note: Note): Chord = Chord(note, "1 b3 5 b7 13")

  def dom7(note: Note): Chord  = Chord(note, "1 3 5 b7")
  def dom9(note: Note): Chord  = Chord(note, "1 3 5 b7 9")
  def dom11(note: Note): Chord = Chord(note, "1 3 5 b7 11")
  def dom13(note: Note): Chord = Chord(note, "1 3 5 b7 13")

  def dim(note: Note): Chord  = Chord(note, "1 b3 b5")
  def dim7(note: Note): Chord = Chord(note, "1 b3 b5 b7")

  def power(note: Note): Chord = Chord(note, "1 5")

  def add2(note: Note): Chord = Chord(note, "1 2 3 5")
  def add4(note: Note): Chord = Chord(note, "1 3 4 5")
  def sus2(note: Note): Chord = Chord(note, "1 2 5")
  def sus4(note: Note): Chord = Chord(note, "1 4 5")
}
