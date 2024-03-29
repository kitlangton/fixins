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

(publish / skip) := true

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
