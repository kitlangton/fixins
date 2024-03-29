/*
rule = SimplifyZIOTypes
 */
package test
import zio._

object SimplifyZIOTypes {
  def task: ZIO[Any, Throwable, Int] = ???

  def uio: ZIO[Any, Nothing, Int] = ???

  def io: ZIO[Any, String, Int] = ???

  def rio: ZIO[String, Throwable, Int] = ???

  def urio: ZIO[String, Nothing, Int] = ???

  def zio: ZIO[String, Int, Int] = ???

  def ioTask: IO[Throwable, Int] = ???

  def ioNothing: IO[Nothing, Int] = ???

  val rlayer: ZLayer[String, Throwable, Int] = ???

  val urlayer: ZLayer[String, Nothing, Int] = ???

  val layer: ZLayer[Any, String, Int] = ???

  val ulayer: ZLayer[Any, Nothing, Int] = ???

  val tasklayer: ZLayer[Any, Throwable, Int] = ???

  val zlayer: ZLayer[String, Int, Int] = ???
}
