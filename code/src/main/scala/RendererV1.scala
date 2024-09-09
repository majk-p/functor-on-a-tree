import cats.Show
import cats.syntax.all.*

class RendererV1 extends Renderer {

  def render[A: Show](tree: Tree[A]): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches =
          branches
            .map(render(_))
            .toList
            .mkString("\n")
        show"$value\n$renderedBranches"

      case Tree.Leaf(value) => show"├── $value"
    }

}
