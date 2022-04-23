package bar.simon.learn.music.domain.music

sealed trait Chord {
  def root: Note
  def formula: String
  def name: String

  lazy val label: String             = s"${root.label} $name"
  lazy val intervals: List[Interval] = formula.split(" ").toList.flatMap(Interval.get)
  lazy val notes: List[Note]         = intervals.map(root.add)

  // TODO: add methods to transform chords (e.g. from major to major7)
}

object Chord {

  val all: List[Note => Chord] = List(
    Power,
    Sus2,
    Sus4,
    Major,
    MajorAdd2,
    MajorAdd4,
    Major7,
    Major9,
    Major11,
    Major13,
    Minor,
    MinorAdd2,
    MinorAdd4,
    Minor7,
    Minor9,
    Minor11,
    Minor13,
    Dominant7,
    Dominant9,
    Dominant11,
    Dominant13,
    Diminished,
    Diminished7
  )

  def findWithName(root: Note, name: String): Option[Chord] =
    all.map(_(root)).find(_.name == name)

  final case class Power(root: Note) extends Chord {
    val formula: String = "1 5"
    val name: String    = "Power"
  }

  final case class Sus2(root: Note) extends Chord {
    val formula: String = "1 2 5"
    val name: String    = "Sus2"
  }

  final case class Sus4(root: Note) extends Chord {
    val formula: String = "1 4 5"
    val name: String    = "Sus4"
  }

  final case class Major(root: Note) extends Chord {
    val formula: String = "1 3 5"
    val name: String    = "Major"
  }

  final case class MajorAdd2(root: Note) extends Chord {
    val formula: String = "1 2 3 5"
    val name: String    = "MajorAdd2"
  }

  final case class MajorAdd4(root: Note) extends Chord {
    val formula: String = "1 3 4 5"
    val name: String    = "MajorAdd4"
  }

  final case class Major7(root: Note) extends Chord {
    val formula: String = "1 3 5 7"
    val name: String    = "Major7"
  }

  final case class Major9(root: Note) extends Chord {
    val formula: String = "1 3 5 9"
    val name: String    = "Major9"
  }

  final case class Major11(root: Note) extends Chord {
    val formula: String = "1 3 5 11"
    val name: String    = "Major11"
  }

  final case class Major13(root: Note) extends Chord {
    val formula: String = "1 3 5 13"
    val name: String    = "Major13"
  }

  final case class Minor(root: Note) extends Chord {
    val formula: String = "1 b3 5"
    val name: String    = "Minor"
  }

  final case class MinorAdd2(root: Note) extends Chord {
    val formula: String = "1 2 b3 5"
    val name: String    = "MinorAdd2"
  }

  final case class MinorAdd4(root: Note) extends Chord {
    val formula: String = "1 b3 4 5"
    val name: String    = "MinorAdd4"
  }

  final case class Minor7(root: Note) extends Chord {
    val formula: String = "1 b3 5 7"
    val name: String    = "Minor7"
  }

  final case class Minor9(root: Note) extends Chord {
    val formula: String = "1 b3 5 9"
    val name: String    = "Minor9"
  }

  final case class Minor11(root: Note) extends Chord {
    val formula: String = "1 b3 5 11"
    val name: String    = "Minor11"
  }

  final case class Minor13(root: Note) extends Chord {
    val formula: String = "1 b3 5 13"
    val name: String    = "Minor13"
  }

  final case class Dominant7(root: Note) extends Chord {
    val formula: String = "1 3 5 b7"
    val name: String    = "Dominant7"
  }

  final case class Dominant9(root: Note) extends Chord {
    val formula: String = "1 3 5 b7 9"
    val name: String    = "Dominant9"
  }

  final case class Dominant11(root: Note) extends Chord {
    val formula: String = "1 3 5 b7 11"
    val name: String    = "Dominant11"
  }

  final case class Dominant13(root: Note) extends Chord {
    val formula: String = "1 3 5 b7 13"
    val name: String    = "Dominant13"
  }

  final case class Diminished(root: Note) extends Chord {
    val formula: String = "1 b3 b5"
    val name: String    = "Diminished"
  }

  final case class Diminished7(root: Note) extends Chord {
    val formula: String = "1 b3 b5 b7"
    val name: String    = "Diminished7"
  }
}
