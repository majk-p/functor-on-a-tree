import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

import snapshot4s.munit.SnapshotAssertions
import snapshot4s.generated.snapshotConfig

class RendererV2Test extends munit.FunSuite with SnapshotAssertions {

  val renderer = RendererV2

  test("should render a simple tree") {
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

  test("should render one level tree".ignore) {
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
      renderer.render(oneLevelTree),
      """/
        |├── bin
        |├── boot
        |├── etc
        |├── home
        |│   └── majk
        |├── root
        |├── usr
        |└── var""".stripMargin
    )
  }
}
