import cats.Show
import cats.syntax.all.*
import BFS.Position

object RendererV3 extends Renderer {

  def render[A: Show](tree: Tree[A]): String = {
    val mapping = BFSExtended.labelNodes(tree).toMap
    renderRecursive(tree, mapping)
  }

  private def renderRecursive[A: Show](
      tree: Tree[A],
      mapping: Map[A, List[Position]]
  ): String = {
    val positions = mapping.get(tree.getValue).getOrElse(List.empty)
    val renderedPrefix = renderPositions(positions)
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches =
          branches.map(renderRecursive(_, mapping)).toList.mkString("\n")
        show"$renderedPrefix $value\n$renderedBranches"

      case Tree.Leaf(value) =>
        show"$renderedPrefix $value"
    }
  }

  private def renderPositions(positions: List[Position]) =
    positions.zipWithIndex
      .map((position, idx) => (position, idx == positions.size - 1))
      .map(renderPosition)
      .mkString

  private def renderPosition(position: Position, isLast: Boolean) =
    position match {
      case Position.First | Position.Middle if (isLast)  => "├──"
      case Position.First | Position.Middle if (!isLast) => "│  "
      case Position.Last if (isLast)                     => "└──"
      case Position.Last if (!isLast)                    => "   "
    }
}
