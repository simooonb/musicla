package bar.simon.learn.music.http.serialization

import bar.simon.learn.music.domain.Sample
import bar.simon.learn.music.domain.answers.{Answer, AnswerVerification}
import bar.simon.learn.music.domain.music.Scale
import bar.simon.learn.music.domain.questions.Question
import bar.simon.learn.music.http.answers.AnswerVerificationCodecs
import com.github.writethemfirst.Approbation
import io.circe.parser.decode
import io.circe.syntax._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec

final class CodecsSpec
    extends FixtureAnyWordSpec
    with Approbation
    with Matchers
    with AnswerVerificationCodecs {

  "Codecs" should {
    "serialize scales" in { approver =>
      approver.verify(Sample.majorC.asJson)
    }

    "deserialize scales" in { approver =>
      approver.verify(prettify(decode[Scale](Sample.cMajorScaleJson)))
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

    "serialize scale notes answers" in { approver =>
      approver.verify(Sample.scaleNotesAnswer.asJson)
    }

    "serialize scale formula answers" in { approver =>
      approver.verify(Sample.scaleFormulaAnswer.asJson)
    }

    "serialize scale harmonization answers" in { approver =>
      approver.verify(Sample.scaleHarmonizationAnswer.asJson)
    }

    "serialize interval answers" in { approver =>
      approver.verify(Sample.notesIntervalAnswer.asJson)
    }

    "deserialize answers" in { approver =>
      approver.verify(prettify(decode[List[Answer]](Sample.answersJson)))
    }

    "serialize good scale notes verification" in { approver =>
      approver.verify(Sample.goodScaleNoteVerification.asJson)
    }

    "deserialize good scale notes verification" in { approver =>
      approver.verify(prettify(decode[AnswerVerification](Sample.goodScaleNoteVerificationJson)))
    }
  }

}
