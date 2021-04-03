package bar.simon.learn.music.http.serialization

import bar.simon.learn.music.domain.Sample
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.http.questions.QuestionsCodecs
import com.github.writethemfirst.Approbation
import io.circe.parser.decode
import io.circe.syntax._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

final class CodecsSpec extends FixtureAnyWordSpec with Approbation with Matchers with QuestionsCodecs {

  "Codecs" should {
    "serialize scales" in { approver =>
      approver.verify(Sample.majorC.asJson)
    }

    "serialize chords" in { approver =>
      approver.verify(Sample.BDim.asJson)
    }

    "serialize scale notes questions" in { approver =>
      approver.verify(Sample.scaleNotesQuestion.asJson)
    }

    "serialize scale formula questions" in { approver =>
      approver.verify(Sample.scaleFormulaQuestion.asJson)
    }

    "serialize scale harmonization questions" in { approver =>
      approver.verify(Sample.scaleHarmonizationQuestion.asJson)
    }

    "serialize interval questions" in { approver =>
      approver.verify(Sample.notesIntervalQuestion.asJson)
    }

    "deserialize questions" in { approver =>
      approver.verify(prettify(decode[List[Question]](Sample.questionsJson)))
    }
  }

}
