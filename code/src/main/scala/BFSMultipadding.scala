import cats.Show
import cats.syntax.all.*
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

import BFS.Position

object BFSMultipadding {

  def labelNodes[A](root: Tree[A]): List[(A, List[Position])] = {
    val q = Queue.empty[(Tree[A], List[Position])]
    val results = ListBuffer.empty[(A, List[Position])]
    q.enqueue((root, List.empty))
    while (q.nonEmpty) {
      val (node, paddings) = q.dequeue()
      node match
        case Tree.Branch(value, branches) =>
          val branchesWithPaddings = attachPaddings(paddings)(branches.toList)
          results.append((value, paddings))
          q.enqueueAll(branchesWithPaddings)
        case Tree.Leaf(value) =>
          results.append((value, paddings))
    }
    results.toList
  }

  private def attachPaddings[A](parentPaddings: List[Position])(
      branches: List[Tree[A]]
  ): List[(Tree[A], List[Position])] =
    branches.zipWithIndex.map { case (tree, index) =>
      (tree, parentPaddings.appended(calculatePadding(index, branches.size)))
    }

  private def calculatePadding(idx: Int, size: Int): Position =
    if (idx == 0) Position.First
    else if (idx == size - 1) Position.Last
    else Position.Middle
}
