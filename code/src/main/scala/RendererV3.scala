import cats.Show
import cats.syntax.all.*
import BFS.Alignment

object RendererV3 extends Renderer {

  def render(tree: Tree[String]): String = {
    val mapping = BFSExtended.labelNodes(tree).toMap
    renderRecursive(tree, mapping)
  }

  private def renderRecursive[A: Show](
      tree: Tree[A],
      mapping: Map[A, List[Alignment]]
  ): String = {
    val alignments = mapping.get(tree.getValue).getOrElse(List.empty)
    val renderedPrefix = renderAlignments(alignments)
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches =
          branches.map(renderRecursive(_, mapping)).toList.mkString("\n")
        show"$renderedPrefix $value\n$renderedBranches"

      case Tree.Leaf(value) =>
        show"$renderedPrefix $value"
    }
  }

  private def renderAlignments(alignments: List[Alignment]) =
    alignments.zipWithIndex
      .map((alignment, idx) => (alignment, idx == alignments.size - 1))
      .map(renderAlignment)
      .mkString

  private def renderAlignment(alignment: Alignment, lastBranch: Boolean) =
    alignment match {
      case Alignment.First | Alignment.Middle if (lastBranch)  => "├──"
      case Alignment.First | Alignment.Middle if (!lastBranch) => "│  "
      case Alignment.Last if (lastBranch)                      => "└──"
      case Alignment.Last if (!lastBranch)                     => "   "
    }
}
