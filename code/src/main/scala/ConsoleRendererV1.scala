import cats.Show

class ConsoleRendererV1 extends Renderer {

  def render[A: Show](tree: Tree[A]): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches = branches.map(render(_)).toList.mkString("\n")
        s"$value\n$renderedBranches"

      case Tree.Leaf(value) => s"├── $value"
    }

}
