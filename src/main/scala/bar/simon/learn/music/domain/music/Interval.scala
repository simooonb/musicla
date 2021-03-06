package bar.simon.learn.music.domain.music

final case class Interval(semiToneLength: Int, naturalNoteOffset: Int, label: String) {
  val toneLength: Double = semiToneLength / 2d
}

object Interval {

  // TODO: handle intervals larger than an octave
  def between(left: Note, right: Note): Option[Interval] = {
    val semiToneLength = math.abs(left.semiToneValue - right.semiToneValue)
    val noteOffset     = math.abs(left.noteIndex - right.noteIndex)

    getByToneAndOffset(semiToneLength, noteOffset).headOption
  }

  def get(label: String): Option[Interval] =
    allByLabel.get(label)

  def getBySemiToneLength(length: Int): List[Interval] =
    all.filter(_.semiToneLength == length)

  def getByNaturalNoteOffset(offset: Int): List[Interval] =
    all.filter(_.naturalNoteOffset == offset)

  def getByToneAndOffset(semiToneLength: Int, offset: Int): List[Interval] =
    getBySemiToneLength(semiToneLength) intersect getByNaturalNoteOffset(offset)

  val unison: Interval                = Interval(0, 0, "1")
  val minorSecond: Interval           = Interval(1, 1, "b2")
  val majorSecond: Interval           = Interval(2, 1, "2")
  val augmentedSecond: Interval       = Interval(3, 1, "2#")
  val doublyAugmentedSecond: Interval = Interval(4, 1, "2##")
  val minorThird: Interval            = Interval(3, 2, "b3")
  val majorThird: Interval            = Interval(4, 2, "3")
  val fourth: Interval                = Interval(5, 3, "4")
  val sharpFourth: Interval           = Interval(6, 3, "#4")
  val flatFifth: Interval             = Interval(6, 4, "b5")
  val fifth: Interval                 = Interval(7, 4, "5")
  val diminishedSixth: Interval       = Interval(7, 5, "d6")
  val minorSixth: Interval            = Interval(8, 5, "b6")
  val majorSixth: Interval            = Interval(9, 5, "6")
  val augmentedSixth: Interval        = Interval(10, 5, "6#")
  val flatMinorSeventh: Interval      = Interval(9, 6, "bb7")
  val minorSeventh: Interval          = Interval(10, 6, "b7")
  val majorSeventh: Interval          = Interval(11, 6, "7")
  val octave: Interval                = Interval(12, 7, "8")
  val majorSecondOctave: Interval     = Interval(14, 8, "9")
  val majorFourthOctave: Interval     = Interval(17, 10, "11")
  val majorSixthOctave: Interval      = Interval(21, 12, "13")

  private val all = List(
    unison,
    minorSecond,
    majorSecond,
    augmentedSecond,
    doublyAugmentedSecond,
    minorThird,
    majorThird,
    fourth,
    sharpFourth,
    flatFifth,
    fifth,
    minorSixth,
    majorSixth,
    flatMinorSeventh,
    minorSeventh,
    majorSeventh,
    octave,
    majorSecondOctave,
    majorFourthOctave,
    majorSixthOctave
  )

  private val allByLabel: Map[String, Interval] = all.map(rn => rn.label -> rn).toMap
}
