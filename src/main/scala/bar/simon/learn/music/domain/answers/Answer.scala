package bar.simon.learn.music.domain.answers

import bar.simon.learn.music.domain.music.{Chord, Interval, Note}

sealed trait Answer

object Answer {

  final case class ScaleNotesAnswer(notes: List[Note])                    extends Answer
  final case class ScaleFormulaAnswer(intervals: List[Interval])          extends Answer
  final case class ScaleHarmonizationAnswer(chords: List[Chord])          extends Answer
  final case class IntervalBetweenNotesAnswer(interval: Option[Interval]) extends Answer // FIXME: remove option

}
