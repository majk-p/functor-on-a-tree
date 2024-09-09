import cats.data.NonEmptyList

enum Tree[A]:
  case Branch(value: A, xbranches: NonEmptyList[Tree[A]])
  case Leaf(value: A)

  def getValue: A = this match
    case Branch(value, _) => value
    case Leaf(value)      => value
