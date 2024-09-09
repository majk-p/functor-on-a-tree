import cats.Show

class ConsoleRendererV2 extends Renderer {

  def render[A: Show](tree: Tree[A]): String =
    renderRecursive(tree, true)

  private def renderRecursive[A: Show](
      tree: Tree[A],
      isLast: Boolean = false
  ): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches =
          (branches.init.map(renderRecursive(_)) :+ renderRecursive(
            branches.last,
            isLast = true
          )).mkString("\n")
        s"$value\n$renderedBranches"

      case Tree.Leaf(value) =>
        if (isLast) s"└── $value"
        else s"├── $value"
    }

}
