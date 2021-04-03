package bar.simon.learn.music.domain.music

sealed trait Alteration {
  val label: String
  val semiToneValue: Int
}

object Alteration {
  val byLabelWithNone: Map[String, Option[Alteration]] =
    ("" -> None :: List(Sharp, SharpSharp, Flat, FlatFlat).map(a => a.label -> Some(a))).toMap

  case object Sharp extends Alteration {
    val label              = "#"
    val semiToneValue: Int = 1
  }

  case object SharpSharp extends Alteration {
    val label              = "##"
    val semiToneValue: Int = 2
  }

  case object Flat extends Alteration {
    val label              = "b"
    val semiToneValue: Int = -1
  }

  case object FlatFlat extends Alteration {
    val label              = "bb"
    val semiToneValue: Int = -2
  }
}
