import cats.Show

trait Renderer {
  def render[A: Show](tree: Tree[A]): String
}
