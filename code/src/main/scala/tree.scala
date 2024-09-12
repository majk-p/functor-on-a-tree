import cats.data.NonEmptyList
import cats.Functor

enum Tree[A] {
  case Branch(value: A, branches: NonEmptyList[Tree[A]])
  case Leaf(value: A)

  def getValue: A = this match
    case Branch(value, _) => value
    case Leaf(value)      => value
}
object Tree {
  given Functor[Tree] = new Functor[Tree] {
    def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =
      fa match
        case Branch(value, branches) =>
          Branch(
            f(value),
            branches.map(map(_)(f))
          )
        case Leaf(value) => Leaf(f(value))
  }

}
