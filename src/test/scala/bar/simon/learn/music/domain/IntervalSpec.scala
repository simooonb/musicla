package bar.simon.learn.music.domain

import bar.simon.learn.music.domain.Sample._
import bar.simon.learn.music.domain.music.Interval
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class IntervalSpec extends AnyWordSpec with Matchers {

  "Intervals" should {
    "be properly computed" in {
      intervals.foreach { case (from, interval, expected) =>
        from.label -> from.add(interval).label shouldBe from.label -> expected.label
      }

    }

    "be found between two notes" in {
      intervals.foreach { case (from, expectedInterval, to) =>
        val result   = (from.label, to.label, Interval.between(from, to).map(_.label))
        val expected = (from.label, to.label, Some(expectedInterval.label))
        result shouldBe expected
      }
    }

    "be found for (doubly) augmented seconds" in {
      Interval.between(GFlat, A.copy(octave = 2)) shouldBe Some(Interval(3, 1, "2#"))
      Interval.between(CFlat, DSharp) shouldBe Some(Interval(4, 1, "2##"))
    }
  }

}
