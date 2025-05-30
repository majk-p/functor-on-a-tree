import cats.data.NonEmptyList
import Tree.Branch
import Tree.Leaf

import snapshot4s.munit.SnapshotAssertions
import snapshot4s.generated.snapshotConfig
import java.time.ZonedDateTime
import cats.Show
import cats.syntax.all.*
import java.util.Date

class Meetuptest extends munit.FunSuite with SnapshotAssertions {

  // workaround for compiler not deriving typeclass for union
  // see also https://github.com/iRevive/union-derivation/releases
  given Show[Event | Talk | Speaker | String] = _ match {
    case v: Event   => v.render
    case v: Talk    => v.render
    case v: Speaker => v.render
    case v: String  => v
  }

  val renderer = RendererV3

  object speakers {
    val katarzyna = Speaker("Katarzyna Marek", "https://www.linkedin.com/in/katarzyna-marek-a74790193")
    val rafal = Speaker("Rafał Piotrowski", "https://www.linkedin.com/in/rafalpiotrowski")
    val jakub = Speaker("Jakub Wojnowski", "https://www.linkedin.com/in/jakub-wojnowski")
    val kacper = Speaker("Kacper Korban", "https://www.linkedin.com/in/kacperfkorban")
    val michal = Speaker("Michał Pawlik", "https://michal.pawlik.dev")
    val tomasz = Speaker("Tomasz Godzik", "https://twitter.com/TomekGodzik")
  }

  object talks {
    // edition 10
    val metals = Talk("All the things that Metals doesn't do")
    val grackle = Talk("Grackle - Scala GraphQL Server")
    // edition 11
    val humanoIDs = Talk("Human(o)IDs — designing IDs for both machines AND humans")
    val scala3Features = Talk("Scala 3 features you probably haven't used (yet)")
    // edition 12
    val functorOnTree = Talk("What does the functor do on the tree?")
    val gearingTowarsOx = Talk("Gearing towards Ox: A look at structured concurrency and direct style Scala")
  }

  object events {
    val event10 = Event(10, "15.05.2024")
    val event11 = Event(11, "2.07.2024")
    val event12 = Event(12, "17.09.2024")
  }

  def renderMeetup(meetupNode: Event | Talk | Speaker | String) =
    meetupNode match {
      case v: Event   => v.render
      case v: Talk    => v.render
      case v: Speaker => v.render
      case v: String  => v
    }

  test("should render a simple tree") {

    val meetup: Tree[Event | Talk | Speaker | String] =
      Branch(
        "Wrocław Scala User Group",
        NonEmptyList
          .of(
            Branch(
              events.event10,
              NonEmptyList.of(
                Branch(talks.metals, NonEmptyList.of(Leaf(speakers.katarzyna))),
                Branch(talks.grackle, NonEmptyList.of(Leaf(speakers.rafal)))
              )
            ),
            Branch(
              events.event11,
              NonEmptyList.of(
                Branch(talks.humanoIDs, NonEmptyList.of(Leaf(speakers.jakub))),
                Branch(talks.scala3Features, NonEmptyList.of(Leaf(speakers.kacper)))
              )
            ),
            Branch(
              events.event12,
              NonEmptyList.of(
                Branch(talks.functorOnTree, NonEmptyList.of(Leaf(speakers.michal))),
                Branch(talks.gearingTowarsOx, NonEmptyList.of(Leaf(speakers.tomasz)))
              )
            )
          )
      )
    val converted: Tree[String] = meetup.map(renderMeetup)
    assertInlineSnapshot(
      renderer.render(converted),
      """ Wrocław Scala User Group
        |├── 📅 15.05.2024 Meeting #10
        |│  ├── 🎤 All the things that Metals doesn't do
        |│  │  └── 🧍 Katarzyna Marek 🌐 https://www.linkedin.com/in/katarzyna-marek-a74790193
        |│  └── 🎤 Grackle - Scala GraphQL Server
        |│     └── 🧍Rafał Piotrowski 🌐 https://www.linkedin.com/in/rafalpiotrowski
        |├── 📅 2.07.2024 Meeting #11
        |│  ├── 🎤 Human(o)IDs — designing IDs for both machines AND humans
        |│  │  └── 🧍 Jakub Wojnowski 🌐 https://www.linkedin.com/in/jakub-wojnowski
        |│  └── 🎤 Scala 3 features you probably haven't used (yet)
        |│     └── 🧍   Kacper Korban 🌐 https://www.linkedin.com/in/kacperfkorban
        |└── 📅 17.09.2024 Meeting #12
        |   ├── 🎤 What does the functor do on the tree?
        |   │  └── 🧍   Michał Pawlik 🌐 https://michal.pawlik.dev
        |   └── 🎤 Gearing towards Ox: A look at structured concurrency and direct style Scala
        |      └── 🧍   Tomasz Godzik 🌐 https://twitter.com/TomekGodzik""".stripMargin
    )
  }

}
