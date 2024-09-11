import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

import snapshot4s.munit.SnapshotAssertions
import snapshot4s.generated.snapshotConfig
import BFS.Position.*

class BFSTest extends munit.FunSuite with SnapshotAssertions {

  test("should visit nodes in expected order") {
    val oneLevelTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList
          .of(
            Leaf("bin"),
            Leaf("boot"),
            Leaf("etc"),
            Branch("home", NonEmptyList.one(Leaf("majk"))),
            Leaf("root"),
            Leaf("usr"),
            Leaf("var")
          )
      )

    assertInlineSnapshot(
      BFS.breadthFirstList(oneLevelTree),
      List("/", "bin", "boot", "etc", "home", "root", "usr", "var", "majk")
    )
  }
  test("should produce paddings") {
    val oneLevelTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList
          .of(
            Leaf("bin"),
            Leaf("boot"),
            Leaf("etc"),
            Branch("home", NonEmptyList.one(Leaf("majk"))),
            Leaf("root"),
            Leaf("usr"),
            Leaf("var")
          )
      )

    assertInlineSnapshot(
      BFS.labelNodes(oneLevelTree),
      List(
        ("/", First),
        ("bin", First),
        ("boot", Middle),
        ("etc", Middle),
        ("home", Middle),
        ("root", Middle),
        ("usr", Middle),
        ("var", Last),
        ("majk", Last)
      )
    )
  }

  test("should produce multi-paddings") {
    assertInlineSnapshot(
      BFSExtended.labelNodes(oneLevelTree),
      List(
        ("/", List()),
        ("bin", List(First)),
        ("boot", List(Middle)),
        ("etc", List(Middle)),
        ("home", List(Middle)),
        ("root", List(Middle)),
        ("usr", List(Middle)),
        ("var", List(Last)),
        ("majk", List(Middle, First))
      )
    )
  }

  test("should produce multi-paddings - with duplicates") {
    val oneLevelTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList
          .of(
            Leaf("bin"),
            Branch("home", NonEmptyList.one(Leaf("majk"))),
            Leaf("root"),
            Leaf("majk")
          )
      )
    assertInlineSnapshot(
      BFSExtended.labelNodes(oneLevelTree),
      List(
        ("/", List()),
        ("bin", List(First)),
        ("home", List(Middle)),
        ("root", List(Middle)),
        ("majk", List(Last)),
        ("majk", List(Middle, First))
      )
    )
  }

  val oneLevelTree: Tree[String] =
    Branch(
      "/",
      NonEmptyList
        .of(
          Leaf("bin"),
          Leaf("boot"),
          Leaf("etc"),
          Branch("home", NonEmptyList.one(Leaf("majk"))),
          Leaf("root"),
          Leaf("usr"),
          Leaf("var")
        )
    )
}
