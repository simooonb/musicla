package bar.simon.learn.music.http.serialization

import io.circe.{Encoder, Json}

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import scala.util.control.NoStackTrace

object ErrorCodecs {

  final case class DeserializationError(message: String) extends RuntimeException(message) with NoStackTrace {
    def getName: String = getClass.getCanonicalName
  }

  def buildErrorResponse(name: String, message: String, at: Option[ZonedDateTime] = None): Json = {
    val err = Json.obj("detail" -> Json.fromString(message), "code" -> Json.fromString(name))
    at match {
      case Some(date) =>
        err.deepMerge(Json.obj("at" -> Json.fromString(date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))))
      case None => err
    }
  }

  implicit final val deserializationErrorEncoder: Encoder[DeserializationError] =
    error => buildErrorResponse(error.getName, error.message)
}

