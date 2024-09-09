---
theme: mp-theme
size: 16:9
transition: slide
# see https://github.com/marp-team/marp-cli/blob/main/docs/bespoke-transitions/README.md#built-in-transitions
marp: true
---



![bg brightness:0.7](./img/forest2.jpg)

<!-- _transition: fade -->

---

<!-- _transition: fade -->

![bg blur:5px brightness:0.5](./img/forest2.jpg)

# What does the functor do on the tree?

<!-- 
![bg blur:5px brightness:0.5](./img/path-dog1.jpg)

# Path for today

1) Model a `Tree` with `ADT`
 * what is a tree
 * normal people see birds or cats on trees
 * our trees are upside down
 * and if we have a really close look, we can see a functor on them
2) Identify the `Functor` on the `Tree`
3) Everyday `Tree` in IT
 * Source code
 * Filesystem and the `tree` command
4) Drawing our own tree
 * Goal: draw a timeline of WSUG
   * First just edition names + times
   * Then subtrees with topics and authors
   * Then sub-subtrees with author details like website or socials
 * Depth first - functional approach
 * Breadth-first - imperative
 * Compile it together
 * Homework: Okasaki structure for FP breadth-first -->


<!-- 
![bg blur:5px brightness:0.4](./img/path-dog1.jpg)

# Path for today

1) Model a `Tree` with `ADT`
2) Identify the `Functor` on the `Tree`
3) Everyday `Tree` in IT
4) Draw yourself a `Tree` -->

<!-- _transition: fade -->

---
<!-- _transition: fade -->

![bg](./img/forest-backpack1.jpg)

---
<!-- _transition: fade -->

# When a regular person goes into the woods

they can spot...

![bg blur:5px brightness:0.4](./img/forest-backpack1.jpg)

---
<!-- _transition: fade -->

![bg](./img/bird1.jpg)

---
<!-- _transition: fade -->

![bg](./img/cat1.jpg)

---
<!-- _transition: fade -->

# But if **you** look carefully

![bg blur:5px brightness:0.4](./img/tree-with-functor-1.jpg)

---
<!-- _transition: fade -->

![bg](./img/tree-with-functor-1.jpg)

---
<!-- _transition: fade -->

![bg](./img/tree-with-functor-2.jpg)

---

![bg](./img/tree-with-functor-3.jpg)

---

<!-- _transition: fade -->

# But first things first

![bg blur:5px brightness:0.4](./img/tree2.jpg)

---

<!-- _transition: drop -->

![bg](./img/tree2.jpg)

---
<!-- _transition: fade -->

![bg](./img/tree2-upside-down.jpg)

---

# That's better!

![bg](./img/tree2-upside-down-diagram.jpg)

---

# Let's model this

---

# Tree

<!-- _class: line-numbers -->

```scala
enum Tree[A]:
  case Branch(value: A, xbranches: NonEmptyList[Tree[A]])
  case Leaf(value: A)
```

---

# Now what?

How's that useful?

---

# Programming languages 🌳

The compiler parses your text file and produces an Abstract Syntax Tree (AST)

- Allow you to analyze and manipulate the syntactic structure of programs
- Useful in meta-programming


---

# AST

![bg 100% right:80%](./img/ast-explorer.png)


---

# Databases 🌳

Self-balancing tree called *B-tree* is a popular way to implement indexing in databases


![bg 100% right:40%](./img/btree.png)

<!-- _footer: Source: https://www.geeksforgeeks.org/introduction-of-b-tree-2/ -->


---

# File system 🌳

- `/` is the `Root`
- Directories are branches
- Files are leaves

---

# Files tree

```bash
.
├── build.sbt
├── docs
│   └── markdown
│       ├── contributing
│       │   ├── how-it-works.md
│       │   ├── index.md
│       │   └── supporting-a-test-framework.md
│       ├── custom-types-support.md
│       └── supported-frameworks.md
├── LICENSE
├── modules
│   ├── core
│   ├── hashing
│   ├── munit
│   ├── plugin
│   ├── scalatest
│   └── weaver
├── project
│   ├── build.properties
│   ├── plugins.sbt
│   ├── project
│   └── Versions.scala
├── README.md
└── website
    ├── babel.config.js
    ├── docs
    ├── docusaurus.config.ts
    └── static
        └── img
            ├── favicon.ico
            ├── logo-large.png
            ├── logo-medium.png
            └── logo-small.png
```

---

# Wait that looked quite nice 🤔

---

# Wait that looked quite nice 🤔

How about we implement a renderer like this for our tree?

---

# Goal 🥅

Draw a tree of meetup editions with topics as sub-trees 🌳 and speaker info as leafs 🍀

<!-- TODO make a slide with showcasing the expected result -->

---

# Renderer

<!-- _class: line-numbers -->

```scala
trait Renderer {
  // `Show` means that we can turn `A` into a nice `String`
  def render[A: Show](tree: Tree[A]): String
}
```
---

# Baby steps 👶

Let's start with drawing this:
```bash
/
├── bin
├── boot
├── etc
├── home
├── root
├── usr
└── var
```

---

# Test


```scala
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

```

---

# Renderer

<!-- _class: line-numbers -->

```scala
  def render[A: Show](tree: Tree[A]): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val renderedBranches =
          branches
            .map(render(_))
            .toList
            .mkString("\n")
        show"$value\n$renderedBranches"

      case Tree.Leaf(value) => show"├── $value"
    }
```

---

# Let's test it!

---

# Snapshot test result

```diff
Snapshot not equal
=> Obtained
/
├── bin
├── boot
├── etc
├── home
├── root
├── usr
├── var
=> Diff (- obtained, + expected)
 ├── usr
-├── var
+└── var
```

---

# The missing `└──`

---

# RendererV2

<!-- _class: line-numbers -->

```scala
  def render[A: Show](tree: Tree[A]): String =
    renderRecursive(tree, true)

  private def renderRecursive[A: Show](tree: Tree[A], isLast: Boolean): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val allButLast = branches.init.map(
          renderRecursive(_, isLast = false)
        )
        val lastBranch = renderRecursive(
          branches.last,
          isLast = true
        )
        val renderedBranches = (allButLast :+ lastBranch).mkString("\n")
        show"$value\n$renderedBranches"

      case Tree.Leaf(value) =>
        if (isLast) show"└── $value"
        else show"├── $value"
    }
```

---

# Test again


```scala
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
```

---

# So far so good!

```bash
RendererV2Test:
  + should render a simple tree 0.266s
```

---

# Nesting 🪜

Can we handle nested structures?

---

# Nesting 🪜

Can we handle nested structures?

```bash
/
├── bin
├── boot
├── etc
├── home
│   └── majk
├── root
├── usr
└── var
```

---

# Let's test it

```scala
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
```

---

# Not quite!

```diff
Snapshot not equal
=> Obtained
/
├── bin
├── boot
├── etc
home
└── majk
├── root
├── usr
└── var
=> Diff (- obtained, + expected)
 ├── etc
-home
-└── majk
+├── home
+│   └── majk
 ├── root
```

---

# Back to the source code

<!-- _class: line-numbers -->

```scala
  def render[A: Show](tree: Tree[A]): String =
    renderRecursive(tree, true)

  private def renderRecursive[A: Show](tree: Tree[A], isLast: Boolean): String =
    tree match {
      case Tree.Branch(value, branches) =>
        val allButLast = branches.init.map(
          renderRecursive(_, isLast = false)
        )
        val lastBranch = renderRecursive(
          branches.last,
          isLast = true
        )
        val renderedBranches = (allButLast :+ lastBranch).mkString("\n")
        show"$value\n$renderedBranches"

      case Tree.Leaf(value) =>
        if (isLast) show"└── $value"
        else show"├── $value"
    }
```

---

# Is this strategy good enough anyway?

<!-- TODO show a step by step visualization, showcase how we lack info about the next branch when visiting with depth-first -->

---

# Depth-first search

![bg 100% right:40%](./img/Depth-First-Search.gif)

<!-- draw the tree from the example above and show how when visiting `majk` leaf we don't know if there are other nodes on the upper level -->

---

# Breadth-first search
![bg 100% right:40%](./img/Animated_BFS.gif)


<!-- 
![](./img/Types-of-Tree-Data-Structure.webp)
https://www.geeksforgeeks.org/introduction-to-tree-data-structure/
 -->