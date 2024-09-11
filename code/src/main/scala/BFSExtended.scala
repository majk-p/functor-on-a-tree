import cats.Show
import cats.syntax.all.*
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

import BFS.Position

object BFSExtended {

  def labelNodes[A](root: Tree[A]): List[(A, List[Position])] = {
    val q = Queue.empty[(Tree[A], List[Position])]
    val results = ListBuffer.empty[(A, List[Position])]
    q.enqueue((root, List.empty))
    while (q.nonEmpty) {
      val (node, positions) = q.dequeue()
      node match
        case Tree.Branch(value, branches) =>
          val branchesWithPositions =
            attachPositions(positions)(branches.toList)
          results.append((value, positions))
          q.enqueueAll(branchesWithPositions)
        case Tree.Leaf(value) =>
          results.append((value, positions))
    }
    results.toList
  }

  private def attachPositions[A](parantPositions: List[Position])(
      branches: List[Tree[A]]
  ): List[(Tree[A], List[Position])] =
    branches.zipWithIndex.map { case (tree, index) =>
      (tree, parantPositions.appended(calculatePosition(index, branches.size)))
    }

  private def calculatePosition(idx: Int, size: Int): Position =
    if (idx == size - 1) Position.Last
    else if (idx == 0) Position.First
    else Position.Middle
}
