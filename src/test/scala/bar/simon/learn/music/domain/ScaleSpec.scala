package bar.simon.learn.music.domain

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Sample._
import bar.simon.learn.music.domain.music.Interval._

final class ScaleSpec extends AnyWordSpec with Matchers {

  "Major scale" should {
    "have the right intervals" in {
      val intervalsExpected = List(unison, majorSecond, majorThird, fourth, fifth, majorSixth, majorSeventh)
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
      val chordsExpected = List(CMaj, DMin, EMin, FMaj, GMaj, AMin2, BDim2).map(_.label)
      majorC.harmonized.map(_.label) should contain theSameElementsInOrderAs chordsExpected
    }
  }

  "Minor scale" should {
    "have the right intervals" in {
      val intervalsExpected = List(unison, majorSecond, minorThird, fourth, fifth, minorSixth, minorSeventh)
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
