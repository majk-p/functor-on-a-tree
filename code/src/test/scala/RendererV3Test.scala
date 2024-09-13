import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

import snapshot4s.munit.SnapshotAssertions
import snapshot4s.generated.snapshotConfig

class RendererV3Test extends munit.FunSuite with SnapshotAssertions {

  val renderer = RendererV3

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
      """ /
        |├── bin
        |├── boot
        |├── etc
        |├── home
        |├── root
        |├── usr
        |└── var""".stripMargin
    )
  }

  test("should render one level tree") {
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
      """ /
        |├── bin
        |├── boot
        |├── etc
        |├── home
        |│  └── majk
        |├── root
        |├── usr
        |└── var""".stripMargin
    )
  }

  test("should render a complex tree") {
    val complexTree: Tree[String] =
      Branch(
        "/",
        NonEmptyList.of(
          Branch(
            "home",
            NonEmptyList.of(
              Branch(
                "Documents",
                NonEmptyList.of(
                  Leaf("report.pdf"),
                  Leaf("thesis.docx")
                )
              ),
              Branch(
                "Projects",
                NonEmptyList.of(
                  Leaf("project1/src/main.scala"),
                  Leaf("project2/test/java/Main.java")
                )
              )
            )
          ),
          Branch(
            "var",
            NonEmptyList.of(
              Leaf("log"),
              Branch(
                "www",
                NonEmptyList.of(
                  Leaf("html"),
                  Leaf("css"),
                  Leaf("js")
                )
              )
            )
          ),
          Branch(
            "usr",
            NonEmptyList.of(
              Branch(
                "local",
                NonEmptyList.of(
                  Branch(
                    "share",
                    NonEmptyList.of(
                      Leaf("man"),
                      Branch(
                        "doc",
                        NonEmptyList.of(
                          Leaf("README.md"),
                          Leaf("LICENSE")
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )

    assertInlineSnapshot(
      renderer.render(complexTree),
      """ /
        |├── home
        |│  ├── Documents
        |│  │  ├── report.pdf
        |│  │  └── thesis.docx
        |│  └── Projects
        |│     ├── project1/src/main.scala
        |│     └── project2/test/java/Main.java
        |├── var
        |│  ├── log
        |│  └── www
        |│     ├── html
        |│     ├── css
        |│     └── js
        |└── usr
        |   └── local
        |      └── share
        |         ├── man
        |         └── doc
        |            ├── README.md
        |            └── LICENSE""".stripMargin
    )
  }
}
