import scala.collection.immutable.Seq

lazy val V = _root_.scalafix.sbt.BuildInfo

inThisBuild(
  List(
    organization := "io.github.kitlangton",
    homepage     := Some(url("https://github.com/kitlangton/fixins")),
    licenses     := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer("kitlangton", "Kit Langton", "kit.langton@gmail.com", url("https://github.com/kitlangton"))
    ),
    scalaVersion           := V.scala213,
    semanticdbEnabled      := true,
    semanticdbIncludeInJar := true,
    semanticdbVersion      := scalafixSemanticdb.revision,
    scalacOptions ++= List("-Yrangepos")
  )
)

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    commands = List("ci-release"),
    name = Some("Publish project"),
    env = Map(
      "PGP_PASSPHRASE"    -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET"        -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)

lazy val root = project
  .in(file("."))
  .settings(
    publish / skip := true
  )
  .aggregate(rules, input, output, tests)

lazy val rules = project.settings(
  moduleName                             := "fixins",
  libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion
)

val zioVersion = "2.0.21"

lazy val input = project.settings(
  (publish / skip) := true,
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % zioVersion
  )
)

lazy val output = project.settings(
  (publish / skip) := true,
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % zioVersion
  )
)

lazy val tests = project
  .settings(
    (publish / skip)                      := true,
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    scalafixTestkitOutputSourceDirectories :=
      (output / Compile / unmanagedSourceDirectories).value,
    scalafixTestkitInputSourceDirectories :=
      (input / Compile / unmanagedSourceDirectories).value,
    scalafixTestkitInputClasspath :=
      (input / Compile / fullClasspath).value
  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)
