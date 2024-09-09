import cats.Show

trait Renderer {
  // `Show` means that we can turn `A` into a nice `String`
  def render[A: Show](tree: Tree[A]): String
}
