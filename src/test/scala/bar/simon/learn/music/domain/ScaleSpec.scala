package bar.simon.learn.music.domain

import bar.simon.learn.music.domain.Interval._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Sample._

final class ScaleSpec extends AnyWordSpec with Matchers {

  "Major scale" should {
    // C D E F G A B
    val majorC = Scale.major(C)
    // A# B# C## D# E# F## G##
    val majorASharp = Scale.major(ASharp)
    // Eb F G Ab Bb C D
    val majorEFlat = Scale.major(EFlat)
    // Fb Gb Ab Bbb Cb Db Eb
    val majorFFlat = Scale.major(FFlat)

    "have the right intervals" in {
      val intervalsExpected = List(root, majorSecond, majorThird, fourth, fifth, majorSixth, majorSeventh)
      majorC.intervals should contain theSameElementsInOrderAs intervalsExpected
    }

    "have the right notes" in {
      val majorCNotesExpected = List(C, D, E, F, G, A, B).map(_.label)
      val majorASharpNotesExpected =
        List(ASharp, BSharp, CSharpSharp, DSharp, ESharp, FSharpSharp, GSharpSharp).map(_.label)
      val majorEFlatNotesExpected = List(EFlat, F, G, AFlat, BFlat, C, D).map(_.label)
      val majorFFlatNotesExpected = List(FFlat, GFlat, AFlat, BFlatFlat, CFlat, DFlat, EFlat).map(_.label)

      majorC.notes.map(_.label) should contain theSameElementsInOrderAs majorCNotesExpected
      majorASharp.notes.map(_.label) should contain theSameElementsInOrderAs majorASharpNotesExpected
      majorEFlat.notes.map(_.label) should contain theSameElementsInOrderAs majorEFlatNotesExpected
      majorFFlat.notes.map(_.label) should contain theSameElementsInOrderAs majorFFlatNotesExpected
    }

    "have the right harmonization" in {
      val chordsExpected = List(CMaj, DMin, EMin, FMaj, GMaj, AMin, BDim).map(_.label)
      majorC.harmonized.map(_.label) should contain theSameElementsInOrderAs chordsExpected
    }
  }

  "Minor scale" should {
    // A B C D E F G
    val minorA = Scale.minor(A)
    // E F# G A B C D
    val minorE = Scale.minor(E)
    // D# E# F# G# A# B C#
    val minorDSharp = Scale.minor(DSharp)
    // Bb C Db Eb F Gb Ab
    val minorBFlat = Scale.minor(BFlat)

    "have the right intervals" in {
      val intervalsExpected = List(root, majorSecond, minorThird, fourth, fifth, minorSixth, minorSeventh)
      minorA.intervals should contain theSameElementsInOrderAs intervalsExpected
    }

    "have the right notes" in {
      val minorANotesExpected      = List(A, B, C, D, E, F, G).map(_.label)
      val minorENotesExpected      = List(E, FSharp, G, A, B, C, D).map(_.label)
      val minorDSharpNotesExpected = List(DSharp, ESharp, FSharp, GSharp, ASharp, B, CSharp).map(_.label)
      val minorBFlatNotesExpected  = List(BFlat, C, DFlat, EFlat, F, GFlat, AFlat).map(_.label)

      minorA.notes.map(_.label) should contain theSameElementsInOrderAs minorANotesExpected
      minorE.notes.map(_.label) should contain theSameElementsInOrderAs minorENotesExpected
      minorDSharp.notes.map(_.label) should contain theSameElementsInOrderAs minorDSharpNotesExpected
      minorBFlat.notes.map(_.label) should contain theSameElementsInOrderAs minorBFlatNotesExpected
    }

    "have the right harmonization" in {
      val chordsExpected = List(AMin, BDim, CMaj, DMin, EMin, FMaj, GMaj).map(_.label)
      minorA.harmonized.map(_.label) should contain theSameElementsInOrderAs chordsExpected
    }
  }

}
