# fixins

> A sumptuous Scalafix smorgasbord.

[![Release Artifacts][Badge-SonatypeReleases]][Link-SonatypeReleases]
[![Snapshot Artifacts][Badge-SonatypeSnapshots]][Link-SonatypeSnapshots]

[Badge-SonatypeReleases]: https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.kitlangton/fixins_2.13.svg "Sonatype Releases"
[Badge-SonatypeSnapshots]: https://img.shields.io/nexus/s/https/oss.sonatype.org/io.github.kitlangton/fixins_2.13.svg "Sonatype Snapshots"
[Link-SonatypeSnapshots]: https://oss.sonatype.org/content/repositories/snapshots/io/github/kitlangton/fixins_2.13/ "Sonatype Snapshots"
[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/io/github/kitlangton/fixins_2.13/ "Sonatype Releases"


## Installation

You can add `fixins` to your project by adding the following line to your `build.sbt`:

```scala
ThisBuild / scalafixDependencies += "io.github.kitlangton" %% "fixins" % "0.0.4"
```

Then you can add the rules to your `.scalafix.conf`:

```scala
rules = [
 SimplifyZIOTypes
]
```

# Rules

### `SimlpifyZIOTypes`

```shell
sbt 'scalafix dependency:SimplifyZIOTypes@io.github.kitlangton::fixins:0.0.4'
```

This rule simplifies ZIO types by replacing `ZIO[R, E, A]` and `ZLayer[R, E, A]` with their more specific type aliases
whenever possible.

```scala
// ZIO Transformations
ZIO[Any, Throwable, Int] => Task[Int]
ZIO[Any, Nothing, Int] => UIO[Int]
ZIO[R, Throwable, Int] => RIO[R, Int]
ZIO[R, Nothing, Int] => URIO[R, Int]

// ZLayer Transformations
ZLayer[Any, Throwable, Int] => TaskLayer[Int]
ZLayer[Any, Nothing, Int] => ULayer[Int]
ZLayer[R, Throwable, Int] => RLayer[R, Int]
ZLayer[R, Nothing, Int] => URLayer[R, Int]
```


