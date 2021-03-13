package bar.simon.learn.music.domain

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Sample._
import bar.simon.learn.music.domain.Interval._

class IntervalSpec extends AnyWordSpec with Matchers {

  "Intervals" should {
    "be properly computed" in {

      val tests = List(
        (A, root, A),
        (A, minorSecond, BFlat),
        (A, majorSecond, B),
        (A, minorThird, C),
        (A, majorThird, CSharp),
        (A, fourth, D),
        (A, sharpFourth, DSharp),
        (A, flatFifth, EFlat),
        (A, fifth, E),
        (A, minorSixth, F),
        (A, majorSixth, FSharp),
        (A, minorSeventh, G),
        (A, majorSeventh, GSharp),
        (EFlat, root, EFlat),
        (EFlat, minorSecond, FFlat),
        (EFlat, majorSecond, F),
        (EFlat, minorThird, GFlat),
        (EFlat, majorThird, G),
        (EFlat, fourth, AFlat),
        (EFlat, sharpFourth, A),
        (EFlat, flatFifth, BFlatFlat),
        (EFlat, fifth, BFlat),
        (EFlat, minorSixth, CFlat),
        (EFlat, majorSixth, C),
        (EFlat, minorSeventh, DFlat),
        (EFlat, majorSeventh, D),
        (B, root, B),
        (B, minorSecond, C),
        (B, majorSecond, CSharp),
        (B, minorThird, D),
        (B, majorThird, DSharp),
        (B, fourth, E),
        (B, sharpFourth, ESharp),
        (B, flatFifth, F),
        (B, fifth, FSharp),
        (B, minorSixth, G),
        (B, majorSixth, GSharp),
        (B, minorSeventh, A),
        (B, majorSeventh, ASharp)
      )

      tests.foreach { case (from, interval, expected) =>
        from.label -> from.add(interval).label shouldBe from.label -> expected.label
      }

    }
  }

}
