package bar.simon.learn.music.domain.music

sealed trait NoteName {
  val semiToneValue: Int
  val noteIndex: Int
}

object NoteName {
  val all = List(A, B, C, D, E, F, G)

  case object A extends NoteName {
    val semiToneValue = 1
    val noteIndex     = 1
  }
  case object B extends NoteName {
    val semiToneValue = 3
    val noteIndex     = 2
  }
  case object C extends NoteName {
    val semiToneValue = 4
    val noteIndex     = 3
  }
  case object D extends NoteName {
    val semiToneValue = 6
    val noteIndex     = 4
  }
  case object E extends NoteName {
    val semiToneValue = 8
    val noteIndex     = 5
  }
  case object F extends NoteName {
    val semiToneValue = 9
    val noteIndex     = 6
  }
  case object G extends NoteName {
    val semiToneValue = 11
    val noteIndex     = 7
  }
}
