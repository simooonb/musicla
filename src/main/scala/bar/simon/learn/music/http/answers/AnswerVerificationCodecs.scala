package bar.simon.learn.music.http.answers

import bar.simon.learn.music.domain.answers.AnswerVerification
import bar.simon.learn.music.http.questions.QuestionsCodecs
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto._

trait AnswerVerificationCodecs extends QuestionsCodecs with AnswersCodecs {

  implicit val answerVerificationDecoder: Decoder[AnswerVerification] = deriveConfiguredDecoder[AnswerVerification]
  implicit val answerVerificationEncoder: Encoder[AnswerVerification] = deriveConfiguredEncoder[AnswerVerification]

}

object AnswerVerificationCodecs extends AnswerVerificationCodecs