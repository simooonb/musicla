package bar.simon.learn.music.domain

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Sample._
import bar.simon.learn.music.domain.music.Interval

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
  }

}
