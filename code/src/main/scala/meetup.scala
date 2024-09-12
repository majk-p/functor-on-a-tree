import cats.Show
import java.util.Date

case class Event(edition: Int, date: Date)
case class Talk(title: String, summary: String = "")
case class Speaker(name: String, social: String)

object Event {
  given Show[Event] = event => s"📅 ${event.date} Meeting #${event.edition}"
}
object Talk {
  given Show[Talk] = talk => s"🎤 ${talk.title}"
}
object Speaker {
  given Show[Speaker] = speaker => f"🧍${speaker.name}%16s 🌐 ${speaker.social}"
}
