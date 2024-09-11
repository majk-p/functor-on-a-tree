import cats.Show
import cats.syntax.all.*

object RendererV2 extends Renderer {

  def render[A: Show](tree: Tree[A]): String =
    renderRecursive(tree, true)

  private def renderRecursive[A: Show](tree: Tree[A], isLast: Boolean): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val allButLast = branches.init.map(
          renderRecursive(_, isLast = false)
        )
        val lastBranch = renderRecursive(
          branches.last,
          isLast = true
        )
        val renderedBranches = (allButLast :+ lastBranch).mkString("\n")
        show"$value\n$renderedBranches"

      case Tree.Leaf(value) =>
        if (isLast) show"└── $value"
        else show"├── $value"
    }

}
