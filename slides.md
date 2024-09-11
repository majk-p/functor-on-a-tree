---
theme: mp-theme
size: 16:9
transition: slide
# see https://github.com/marp-team/marp-cli/blob/main/docs/bespoke-transitions/README.md#built-in-transitions
marp: true
---

```scala mdoc:invisible
import scala.io.Source

def sourceFromFile(file: String, fromTo: Option[(Int, Int)] = None) = println({
  fromTo match {
    case None => linesFromFile(file)
    case Some((from, to)) =>
      linesFromFile(file).zipWithIndex
        .filter { case (_, line) =>
          (line+1) >= from && (line+1) <= to
        }
        .map(_._1)
  }
}.mkString("```scala\n", "\n", "\n```"))

def linesFromFile(file: String) =
  Source.fromFile(file).getLines().toList
```


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

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/tree.scala", Some(3, 5))
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

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/renderer.scala", Some(3, 6))
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


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV1Test.scala", Some(12, 33))
```

---

# Renderer

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV1.scala", Some(6, 17))
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

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV2.scala", Some(6, 25))
```

---

# Test again


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV2Test.scala", Some(12, 32))
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

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV2Test.scala", Some(34, 62))
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

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV2.scala", Some(6, 25))
```


---

# Depth-first search

![bg 100% right:40%](./img/Depth-First-Search.gif)

<!-- _footer: Source: https://en.wikipedia.org/wiki/Depth-first_search -->

<!-- draw the tree from the example above and show how when visiting `majk` leaf we don't know if there are other nodes on the upper level -->


---

# Depth-first search

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV1.scala", Some(6, 17))
```

![bg 100% right:40%](./img/Depth-First-Search.gif)

<!-- _footer: Source: https://en.wikipedia.org/wiki/Depth-first_search -->

<!-- draw the tree from the example above and show how when visiting `majk` leaf we don't know if there are other nodes on the upper level -->


---

# Is this strategy good enough anyway?

---
<!-- _transition: fade -->
# Step by step

Let's do depth first search on a simplified tree

```bash
/
├── bin
├── boot
├── home
│   └── majk
└── root
```

![bg 100% left:40%](./img/directories-background.png)

---
<!-- _transition: fade -->
# Step by step

```bash
/
├── bin
...
```

![bg 100% left:40%](./img/directories-background-bin.png)

---
<!-- _transition: fade -->
# Step by step

```bash
/
├── bin
├── boot
...
```

![bg 100% left:40%](./img/directories-background-boot.png)

---
<!-- _transition: fade -->
# Step by step

```bash
/
├── bin
├── boot
├── home
...
```

![bg 100% left:40%](./img/directories-background-home.png)

---

# Step by step

```bash
/
├── bin
├── boot
├── home
❓  └── majk
```
🤔 what should the first character be?

![bg 100% left:40%](./img/directories-background-majk.png)


---

# Limitations of DFS

* We want to draw trees in depth-first manner, but we lack information about the structure
* We need to learn the tree width first, before deciding how to print nodes

---
<!-- _transition: fade -->

# Breadth-first search
![bg 100% right:40%](./img/Animated_BFS.gif)

<!-- _footer: Source https://en.wikipedia.org/wiki/Breadth-first_search -->

---

# Breadth-first search

**Black:** explored

**Grey:** queued to be explored later on

* You got this right, we're introducing a mutable queue 😲

![bg 100% right:40%](./img/Animated_BFS.gif)

<!-- _footer: Source https://en.wikipedia.org/wiki/Breadth-first_search -->

---

# Basic BFS

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", Some(7, 11))
```
🙈
```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", Some(20, 22))
```

---

# Basic BFS

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", Some(8, 23))
```

---

# Let's test it

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/BFSTest.scala", Some(10, 31))
```

---

# Let's see it in action

```scala
sbt:root> testOnly *BFS*
Visiting     /, queue: Queue()
Visiting   bin, queue: Queue(boot, etc, home, root, usr, var)
Visiting  boot, queue: Queue(etc, home, root, usr, var)
Visiting   etc, queue: Queue(home, root, usr, var)
Visiting  home, queue: Queue(root, usr, var)
Visiting  root, queue: Queue(usr, var, majk)
Visiting   usr, queue: Queue(var, majk)
Visiting   var, queue: Queue(majk)
Visiting  majk, queue: Queue()
BFSTest:
  + should visit nodes in expected order 0.124s
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
```

---

# So far so good, ordering makes sense

Now let's attach some info along the way

---

# Node positioning

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", Some(25, 29))
```

---

# Node positioning

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", Some(25, 29))
```

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(10,10))
```

---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(10,14))
```

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(23,25))
```


---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(14,23))
```


---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(14,23))
```

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", Some(27,32))
```

---

# Let's test it

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/BFSTest.scala", Some(63, 79))
```

<!-- NOTE: we are not handling duplicates here, that's a bonus question -->

---

# Works like a charm

```scala
BFSTest:
  + should visit nodes in expected order 0.078s
  + should produce paddings 0.018s
  + should produce extended positions 0.007s
```

---

# We are ready


---

# Final challenge

Let's implement `RendererV3` 🚀





---

# Bonus

B-trees https://planetscale.com/blog/btrees-and-database-indexes

<!-- 
![](./img/Types-of-Tree-Data-Structure.webp)
https://www.geeksforgeeks.org/introduction-to-tree-data-structure/
 -->