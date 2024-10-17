import cats.Show
import cats.syntax.all.*
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

import BFS.Alignment

object BFSExtended {

  def labelNodes[A](root: Tree[A]): List[(A, List[Alignment])] = {
    val q = Queue.empty[(Tree[A], List[Alignment])]
    val results = ListBuffer.empty[(A, List[Alignment])]
    q.enqueue((root, List.empty))
    while (q.nonEmpty) {
      val (node, alignments) = q.dequeue()
      node match
        case Tree.Branch(value, branches) =>
          val branchesWithPositions =
            attachPositions(alignments)(branches.toList)
          results.append((value, alignments))
          q.enqueueAll(branchesWithPositions)
        case Tree.Leaf(value) =>
          results.append((value, alignments))
    }
    results.toList
  }

  private def attachPositions[A](parantPositions: List[Alignment])(
      branches: List[Tree[A]]
  ): List[(Tree[A], List[Alignment])] =
    branches.zipWithIndex.map { case (tree, index) =>
      (tree, parantPositions.appended(calculatePosition(index, branches.size)))
    }

  private def calculatePosition(idx: Int, size: Int): Alignment =
    if (idx == size - 1) Alignment.Last
    else if (idx == 0) Alignment.First
    else Alignment.Middle
}
