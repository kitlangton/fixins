package test
import zio._

object SimplifyZIOTypes {
  def task: Task[Int] = ???

  def uio: UIO[Int] = ???

  def io: IO[String, Int] = ???

  def rio: RIO[String, Int] = ???

  def urio: URIO[String, Int] = ???

  def zio: ZIO[String, Int, Int] = ???

  def ioTask: Task[Int] = ???

  def ioNothing: UIO[Int] = ???

  val rlayer: RLayer[String, Int] = ???

  val urlayer: URLayer[String, Int] = ???

  val layer: Layer[String, Int] = ???

  val ulayer: ULayer[Int] = ???

  val tasklayer: TaskLayer[Int] = ???

  val zlayer: ZLayer[String, Int, Int] = ???
}
