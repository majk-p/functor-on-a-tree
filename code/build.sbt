import snapshot4s.BuildInfo.snapshot4sVersion

scalaVersion := "3.4.1"

testFrameworks += new TestFramework("munit.Framework")

libraryDependencies += "org.typelevel" %% "cats-core" % "2.12.0"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.1" % Test,
  "org.typelevel" %% "cats-core" % "2.12.0" % Test,
  "com.siriusxm" %% "snapshot4s-munit" % snapshot4sVersion % Test
)

val root = (project in file(".")).enablePlugins(Snapshot4sPlugin)
