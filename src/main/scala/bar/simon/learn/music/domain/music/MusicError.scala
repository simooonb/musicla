package bar.simon.learn.music.domain.music

import scala.util.control.NoStackTrace

trait MusicError extends Exception with Product with Serializable with NoStackTrace
