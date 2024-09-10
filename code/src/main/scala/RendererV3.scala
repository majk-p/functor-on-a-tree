import cats.Show
import cats.syntax.all.*
import scala.collection.mutable.Queue

class RendererV3 extends Renderer {

  def render[A: Show](tree: Tree[A]): String = {
    println(breadthFirstList(tree))
    "???"
  }

  private def breadthFirstList[A](root: Tree[A]): List[A] = {
    val q = Queue.empty[Tree[A]]
    val results = scala.collection.mutable.ListBuffer.empty[A]
    q.enqueue(root)
    while (q.nonEmpty) {
      val node = q.dequeue()
      node match
        case Tree.Branch(value, branches) =>
          results.append(value)
          q.enqueueAll(branches.toList)
        case Tree.Leaf(value) =>
          results.append(value)

    }
    results.toList
  }
  // private def labelNodes[A, Label](root: Tree[A])(f: A => Label): List[Tree[(A, Label)]] = {
  //   val q = Queue.empty[Tree[A]]
  //   val results = Queue.empty[Tree[(A, Label)]]
  //   q.enqueue(root)
  //   while(q.nonEmpty) {
  //     val node = q.dequeue()
  //     node match
  //       case Tree.Branch(value, branches) =>
  //       case Tree.Leaf(value) =>

  //   }
  //   ???
  // }
}
