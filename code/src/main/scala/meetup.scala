import cats.Show

case class Event(edition: Int, date: String) {
  val render = s"📅 ${date} Meeting #${edition}"
}
case class Talk(title: String) {
  val render = s"🎤 ${title}"
}
case class Speaker(name: String, social: String) {
  val render = f"🧍${name}%16s 🌐 ${social}"
}
