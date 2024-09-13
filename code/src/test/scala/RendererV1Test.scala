import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

import snapshot4s.munit.SnapshotAssertions
import snapshot4s.generated.snapshotConfig

class RendererV1Test extends munit.FunSuite with SnapshotAssertions {

  val renderer = RendererV1

  test("should render a simple tree".ignore) {
    val oneLevelTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList
          .of("bin", "boot", "etc", "home", "root", "usr", "var")
          .map(Leaf(_))
      )

    assertInlineSnapshot(
      renderer.render(oneLevelTree),
      """/
        |├── bin
        |├── boot
        |├── etc
        |├── home
        |├── root
        |├── usr
        |└── var""".stripMargin
    )
  }

}
