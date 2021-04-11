package bar.simon.learn.music.domain.music

sealed trait NoteName {
  def label: String
  def semiToneValue: Int
  def noteIndex: Int
}

object NoteName {
  def findByName(name: String): Option[NoteName] =
    all.find(_.label == name.head.toString.toUpperCase)

  val all = List(A, B, C, D, E, F, G)

  case object A extends NoteName {
    val label         = "A"
    val semiToneValue = 1
    val noteIndex     = 1
  }
  case object B extends NoteName {
    val label         = "B"
    val semiToneValue = 3
    val noteIndex     = 2
  }
  case object C extends NoteName {
    val label         = "C"
    val semiToneValue = 4
    val noteIndex     = 3
  }
  case object D extends NoteName {
    val label         = "D"
    val semiToneValue = 6
    val noteIndex     = 4
  }
  case object E extends NoteName {
    val label         = "E"
    val semiToneValue = 8
    val noteIndex     = 5
  }
  case object F extends NoteName {
    val label         = "F"
    val semiToneValue = 9
    val noteIndex     = 6
  }
  case object G extends NoteName {
    val label         = "G"
    val semiToneValue = 11
    val noteIndex     = 7
  }
}
