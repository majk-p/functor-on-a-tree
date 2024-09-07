import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

class RendererTest extends munit.FunSuite {

  val renderer = new ConsoleRenderer
  test("should render a simple tree") {
    val oneLevelTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList
          .of("bin", "boot", "etc", "home", "root", "usr", "var")
          .map(Leaf(_))
      )
    val rendered = renderer.render(oneLevelTree)
    val expected = """/
                     |├── bin
                     |├── boot
                     |├── etc
                     |├── home
                     |├── root
                     |├── usr
                     |└── var
                     |""".stripMargin
    assertEmptyDiff(rendered, expected)
  }

  test("should render a simple tree") {
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
    val rendered = renderer.render(oneLevelTree)
    val expected = """/
                    |├── bin
                    |├── boot
                    |├── etc
                    |├── home
                    |│   └── majk
                    |├── root
                    |├── usr
                    |└── var
                    |""".stripMargin
    assertEmptyDiff(rendered, expected)
  }

  private def assertEmptyDiff(result: String, expected: String) = {
    val diff = munit.diff.Diff(result, expected)
    assert(
      diff.isEmpty,
      diff.createReport(
        "Result didn't match expectation",
        printObtainedAsStripMargin = false
      )
    )
  }

}
