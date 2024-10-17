---
theme: mp-theme
size: 16:9
transition: slide
# see https://github.com/marp-team/marp-cli/blob/main/docs/bespoke-transitions/README.md#built-in-transitions
marp: true
---

```scala mdoc:invisible
import scala.io.Source

def sourceFromFile(file: String, fromTo: (Int, Int)*) = println({
  if(fromTo.isEmpty) linesFromFile(file)
  else fromTo.flatMap { case (from, to) =>
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

# When people go into the woods

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

# But if they look **very** carefully

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

# Hi 👋 My name is Michał Pawlik

---
<!-- _transition: fade -->


![bg](./img/LDN-WRO.png)

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
sourceFromFile("code/src/main/scala/tree.scala", (4, 6))
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

# Goal 🥅

```bash
 Wrocław Scala User Group
├── 📅 15.05.2024 Meeting #10
│  ├── 🎤 All the things that Metals doesn't do
│  │  └── 🧍 Katarzyna Marek 🌐 https://www.linkedin.com/in/katarzyna-marek-a74790193
│  └── 🎤 Grackle - Scala GraphQL Server
│     └── 🧍Rafał Piotrowski 🌐 https://www.linkedin.com/in/rafalpiotrowski
├── 📅 2.07.2024 Meeting #11
│  ├── 🎤 Human(o)IDs — designing IDs for both machines AND humans
│  │  └── 🧍 Jakub Wojnowski 🌐 https://www.linkedin.com/in/jakub-wojnowski
│  └── 🎤 Scala 3 features you probably haven't used (yet)
│     └── 🧍   Kacper Korban 🌐 https://www.linkedin.com/in/kacperfkorban
└── 📅 17.09.2024 Meeting #12
   ├── 🎤 What does the functor do on the tree?
   │  └── 🧍   Michał Pawlik 🌐 https://michal.pawlik.dev
   └── 🎤 Gearing towards Ox: A look at structured concurrency and direct style Scala
      └── 🧍   Tomasz Godzik 🌐 https://twitter.com/TomekGodzik
```

---

# But how 🤔

* Renderer capable of drawing **simple** structure
* Renderer capable of drawing **nested** structure
* Model meetup details
* Render tree with meetup details

---

# Renderer

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/renderer.scala", (1, 3))
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
sourceFromFile("code/src/test/scala/RendererV1Test.scala", (12, 33))
```

---

# Snapshot4s

https://github.com/siriusxm/snapshot4s

![bg right:30% 80%](data:image/gif;base64,R0lGODlhhACEAJEAAAAAAP///wAAAAAAACH5BAEAAAIALAAAAACEAIQAAAL/jI+py+0Po5y02ouz3rz7D4biSJbmiabqyrbuC8chQNf2jedUDiA2w+P5gkThpIi87XSH3yJZG0KRy2mxqmxGn1OpNYj9MiVGg1Nh9YpxWMy5QdWyuTTIu33cWu50sDyLpgcniEdGmFf3EGc2lsAXmLh36PAYUanWWOY4uRlZcdmHOcfouRhQqekFJGgKCtnzp/nWypn6t1p6dYsIS+rnKwp4WtuoGhqbyYnbO0xk7CtLPNrJ/Nz8e114HZ3rGbxWvQuMPO1K/c2a7k0OHj7+vp28TqmOPSvdjlqP3u0+2M+Nmb58AwXWC6iNFkB81greW1hO2TFo8gxOa7ivz0OL//YkvuLHMdgyjPPi9QNpzc7BiiInZhNHbSNKmCpL1uQYcKM5mRfMpeQ1kyJEfySJKvJ4rufKiEyFjaQJNKqkoTqXGoXn0+VNgiFh8sSaUWi7MASLmvUa1uRYXlzPgj2JluoasvncvnzbFW9bGWp3puUbw9RTeIBhCHaZtXCJwx+hKh7cV9crxv/yxuyyoSqUZZQhO8SsQXMSzpIt/WXXWWpkZxpL3/wpNrUh1mKLiobcMjbSxKtxps3JsLVcm44rA4dbmx1u5ah3Iw2q1rbV5cl1EyestGmauB2bvl5xfDT38LCTqgiv0Lf2043PV9yu9z178zPmkx4uzO/1yuW/Y/+ntx5td/GXnWUJPcecdfk5t59nCLY34FZWIXRUg4g9mAF8l3mnVXH3XVXfZsItWFJBdIEIgobQmdghgE6doGJzyNHX34gcfBXdiLzl1ptsEVZomS0KuiGfGP6ZNqOQvREZ4BdHHnmbjhjyuJeLTIpX3YYgUpgldDeCluNkDJKonj8+nkiejUDiqOSZbJXWZjFqxleia+AFh1eNCsb432J4Dojjh+BQ52edSdono5MtFmrmb4juCaaeBM5GZpRicphnghlimGaXidpl6aYWZtmplmw6elGBJ1Ip55DcSTeqcUU2KiCrIkJ6aKzpfZYqqWBqGOiaswqKj2joseShlEP/EWtopXD++iiNF9Y6XquTLpkspVDaSeeLVn6aLZJbTvinm90GO9W4+DlI7beu9nlgs6u++mav18ZLK4qaoiurvYRuK2+9QXLK7b2qsUjpqboW/C+QsJL5IUhcHbtwrt3la+zEApoLKLkz2qoxtO1em/HH+4YMLMN3Gikspv1iGdhcLUM8M8ckDFpztDjLsPPL/EpsLbOKapvzstX6K3DPTypb5sM7Ms2nwZc6a7GSaMpMtIullqyvu59MV5zVmhIqNoT48lt2mFKrvXTWavOqrrfSeur12fOljXexBK9LL930wZ3el3xn6rddQLt85Yqoym14olyKWqbCTTscNtY/pKaruNEQoh001HtHvjjCHTfLW9TsXswjqHpXDDPAqI/d9+VsF834tK8TLjvXKD997uKN97577V4HHrvowd/69eCjDxwwrrsnrqrt+sUtuaQN13103BJabP2ibS8//fbMG6iaNg9/D/Lv4laJLfa4Z961z5b7aPzqNIcWspeLcr7xlJ4jT79y2e9njymgAQ+IwAQqcIEMbKADHwjBCEpwghRsYAEAADs=)

---

# Renderer

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV1.scala", (6, 17))
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


# Renderer

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV1.scala", (6, 17))
```


---

# RendererV2

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV2.scala", (6, 25))
```

---

# Test again


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV2Test.scala", (12, 32))
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
sourceFromFile("code/src/test/scala/RendererV2Test.scala", (34, 62))
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
sourceFromFile("code/src/main/scala/RendererV2.scala", (6, 25))
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
sourceFromFile("code/src/main/scala/RendererV1.scala", (6, 17))
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
sourceFromFile("code/src/main/scala/BFS.scala", (7, 12))
```
🙈
```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", (21, 22))
```

---

# Basic BFS

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", (8, 23))
```

---

# Let's test it

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/BFSTest.scala", (10, 31))
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
sourceFromFile("code/src/main/scala/BFS.scala", (25, 29))
```

---

# Node positioning

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFS.scala", (25, 29))
```

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", (10,10))
```

---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", (10,14))
```

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", (24,25))
```


---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", (14,23))
```


---

# Extended queue and result type

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/BFSExtended.scala", (27,32))
```
---

# Let's test it

For our test tree

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
sourceFromFile("code/src/test/scala/BFSTest.scala", (63, 79))
```

<!-- NOTE: we are not handling duplicates here, that's a bonus question -->

---

# Works like a charm

```scala
BFSTest:
  + should visit nodes in expected order 0.078s
  + should produce paddings 0.018s
  + should produce extended alignments 0.007s
```

---

# We are ready

---

# We are ready

- `RendererV3` should first do BFS to learn the structure, 
- then DFS to draw in correct order

---
<!-- _class: line-numbers -->
# `RendererV3` implementation

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV3.scala", (5, 10), (43, 43))
```

---
<!-- _class: line-numbers -->
# `RendererV3` implementation

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV3.scala", (12, 27))
```

---
<!-- _class: line-numbers -->
# `RendererV3` implementation


```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/RendererV3.scala", (29, 41))
```

---


# `RendererV3` test

```scala
val complexTree: Tree[String] =
```

---


# `RendererV3` test


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV3Test.scala", (65, 126))
```

---

# `RendererV3` test


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/RendererV3Test.scala", (64, 68), (126, 126), (128, 152))
```

---

# Final challenge

![](./img/meetup.png)


---

# Model the data 

---

# Model the data 


```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/meetup.scala", (3, 11))
```

---

# Model the data 


```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/MeetupTest.scala", (25, 50))
```

---

# Tree of `String`

* We know how to print `Tree[String]`
* We need to turn `Tree[Event | Talk | Speaker | String]` into `Tree[String]`


---

# Do you know what time it is? 🕔

---

<!-- _transition: drop -->

# It's time for...

---

<!-- _transition: drop -->


# The F word

---

# Functor

---

# Functor

Provided we have `Event | Talk | Speaker| String => String`

Functor can turn `Tree[Event | Talk | Speaker | String]` into `Tree[String]`

---


# `Functor[F[_]]` on a `Tree[A]` 🌳

---


# `Functor` on a `Tree[A]` 🌳

<!-- _class: line-numbers -->

```scala mdoc:passthrough
sourceFromFile("code/src/main/scala/tree.scala", (12, 24))
```
---

# `Functor[F[_]]` on a `Tree[A]` 🌳

Functor can turn `Tree[Event | Talk | Speaker | String]` into `Tree[String]`

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/MeetupTest.scala", (52, 58))
```
```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/MeetupTest.scala", (62, 62), (90, 90))
```
---

# `Functor[F[_]]` on a `Tree[A]` 🌳

```scala mdoc:passthrough
sourceFromFile("code/src/test/scala/MeetupTest.scala", (62, 89))
```

---
<!-- _transition: fade -->

# Final result

![bg blur:5px brightness:0.3](./img/forest2.jpg)


---
<!-- _transition: fade -->

# Final result

```bash
 Wrocław Scala User Group
├── 📅 15.05.2024 Meeting #10
│  ├── 🎤 All the things that Metals doesn't do
│  │  └── 🧍 Katarzyna Marek 🌐 https://www.linkedin.com/in/katarzyna-marek-a74790193
│  └── 🎤 Grackle - Scala GraphQL Server
│     └── 🧍Rafał Piotrowski 🌐 https://www.linkedin.com/in/rafalpiotrowski
├── 📅 2.07.2024 Meeting #11
│  ├── 🎤 Human(o)IDs — designing IDs for both machines AND humans
│  │  └── 🧍 Jakub Wojnowski 🌐 https://www.linkedin.com/in/jakub-wojnowski
│  └── 🎤 Scala 3 features you probably haven't used (yet)
│     └── 🧍   Kacper Korban 🌐 https://www.linkedin.com/in/kacperfkorban
└── 📅 17.09.2024 Meeting #12
   ├── 🎤 What does the functor do on the tree?
   │  └── 🧍   Michał Pawlik 🌐 https://michal.pawlik.dev
   └── 🎤 Gearing towards Ox: A look at structured concurrency and direct style Scala
      └── 🧍   Tomasz Godzik 🌐 https://twitter.com/TomekGodzik
```
![bg blur:5px brightness:0.3](./img/forest2.jpg)

---
<!-- _transition: fade -->

# Takeaways

![bg blur:5px brightness:0.3](./img/forest2.jpg)

* Kinds of traversal algorithms - DFS and BFS
* Some of them are specialized like our labeling and rendering
* Others are generic like `map`
* Mixing imperative and FP is good and can be fun!

---
<!-- _transition: fade -->


![bg blur:5px brightness:0.3](./img/forest2.jpg)


# Thank you!

<style scoped>
/* Styling for centering (required in default theme) */
h1, h2, h3, h4, h5, p, ul, li {
  text-align: center;
}
</style>

Keep in touch! 🤝

Blog: [blog.michal.pawlik.dev](https://blog.michal.pawlik.dev)
Linkedin: [Michał Pawlik](https://www.linkedin.com/in/michał-pawlik/)
Github: [majk-p](https://github.com/majk-p)
Mastodon: [majkp@hostux.social](https://hostux.social/@majkp)

---

![bg blur:5px brightness:0.3](./img/forest2.jpg)


# Thank you!

<style scoped>
/* Styling for centering (required in default theme) */
h1, h2, h3, h4, h5, p, ul, li {
  text-align: center;
}
</style>

Keep in touch! 🤝

![width:350px](./img/qr-code-repo.png)

Slides available at https://github.com/majk-p/functor-on-a-tree

---

# Bonus
<!-- _transition: fade -->

![bg blur:2px brightness:0.3](./img/tree2-upside-down.jpg)


- B-trees https://planetscale.com/blog/btrees-and-database-indexes
- Purely functional data structures
  - BFS https://www.cs.tufts.edu/~nr/cs257/archive/chris-okasaki/breadth-first.pdf
  - https://cstheory.stackexchange.com/questions/1539/whats-new-in-purely-functional-data-structures-since-okasaki
