import cats.Show
import cats.syntax.all.*
import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

object BFS {

  def breadthFirstList[A](root: Tree[A]): List[A] = {
    val q = Queue.empty[Tree[A]]
    val results = ListBuffer.empty[A]
    q.enqueue(root)
    while (q.nonEmpty) {
      val node = q.dequeue()
      println(f"Visiting ${node.getValue}%5s, queue: ${q.map(_.getValue)}")
      node match
        case Tree.Branch(value, branches) =>
          results.append(value)
          q.enqueueAll(branches.toList)
        case Tree.Leaf(value) =>
          results.append(value)
    }
    results.toList
  }

  enum Position {
    case First
    case Middle
    case Last
  }

  def labelNodes[A](root: Tree[A]): List[(A, Position)] = {
    val q = Queue.empty[(Tree[A], Position)]
    val results = ListBuffer.empty[(A, Position)]
    q.enqueue((root, Position.First))
    while (q.nonEmpty) {
      val (node, position) = q.dequeue()
      println(f"Visiting ${node.getValue}%5s, queue: ${q.map(_._1.getValue)}")
      node match
        case Tree.Branch(value, branches) =>
          val branchesWithPositions = attachPosition(branches.toList)
          results.append((value, position))
          q.enqueueAll(branchesWithPositions)
        case Tree.Leaf(value) =>
          results.append((value, position))
    }
    results.toList
  }

  private def attachPosition[A](
      branches: List[Tree[A]]
  ): List[(Tree[A], Position)] =
    branches.zipWithIndex.map { case (tree, index) =>
      (tree, calculatePosition(index, branches.size))
    }

  private def calculatePosition(idx: Int, size: Int): Position =
    if (idx == size - 1) Position.Last
    else if (idx == 0) Position.First
    else Position.Middle
}
