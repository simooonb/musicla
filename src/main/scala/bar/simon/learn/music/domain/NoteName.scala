package bar.simon.learn.music.domain

sealed trait NoteName {
  val semiToneValue: Int
}

object NoteName {
  val all = List(A, B, C, D, E, F, G)

  case object A extends NoteName {
    val semiToneValue = 1
  }
  case object B extends NoteName {
    val semiToneValue = 3
  }
  case object C extends NoteName {
    val semiToneValue = 4
  }
  case object D extends NoteName {
    val semiToneValue = 6
  }
  case object E extends NoteName {
    val semiToneValue = 8
  }
  case object F extends NoteName {
    val semiToneValue = 9
  }
  case object G extends NoteName {
    val semiToneValue = 11
  }
}
