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

  enum Alignment {
    case First
    case Middle
    case Last
  }

  def labelNodes[A](root: Tree[A]): List[(A, Alignment)] = {
    val q = Queue.empty[(Tree[A], Alignment)]
    val results = ListBuffer.empty[(A, Alignment)]
    q.enqueue((root, Alignment.First))
    while (q.nonEmpty) {
      val (node, alignment) = q.dequeue()
      println(f"Visiting ${node.getValue}%5s, queue: ${q.map(_._1.getValue)}")
      node match
        case Tree.Branch(value, branches) =>
          val branchesWithPositions = attachPosition(branches.toList)
          results.append((value, alignment))
          q.enqueueAll(branchesWithPositions)
        case Tree.Leaf(value) =>
          results.append((value, alignment))
    }
    results.toList
  }

  private def attachPosition[A](
      branches: List[Tree[A]]
  ): List[(Tree[A], Alignment)] =
    branches.zipWithIndex.map { case (tree, index) =>
      (tree, calculatePosition(index, branches.size))
    }

  private def calculatePosition(idx: Int, size: Int): Alignment =
    if (idx == size - 1) Alignment.Last
    else if (idx == 0) Alignment.First
    else Alignment.Middle
}
