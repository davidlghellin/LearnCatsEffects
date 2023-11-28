package es.david.cap01.io

import cats.effect.IO
import scala.io.StdIn
object IoIntroduccionEjercicios {

  // 1 - sequence two IOs and take the result of the LAST one
  // hint: use flatMap
  // 2 - sequence two IOs and take the result of the FIRST one
  // hint: use flatMap
  // 3 - repeat an IO effect forever
  // hint: use flatMap + recursion
  // 4 - convert an IO to a different type
  // hint: use map
  // 5 - discard value inside an IO, just return Unit
  // 6 - fix stack recursion
  // 7 (hard) - write a fibonacci IO that does NOT crash on recursion
  // 1
  def sequenceTakeLast[A, B](ioa: IO[A], iob: IO[B]): IO[B] =
    for {
      _ <- ioa
      b <- iob
    } yield b

  def sequenceTakeLast_v1[A, B](ioa: IO[A], iob: IO[B]): IO[B] =
    ioa.flatMap(_ => iob)

  def sequenceTakeLast_v2[A, B](ioa: IO[A], iob: IO[B]): IO[B] =
    ioa *> iob // andThen

  def sequenceTakeLast_v3[A, B](ioa: IO[A], iob: IO[B]): IO[B] =
    ioa >> iob // andThen con by-name call

  // 2
  def sequenceTakeFirst[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
    ioa.flatMap(a => iob.map(_ => a))

  def sequenceTakeFirst_v1[A, B](ioa: IO[A], iob: IO[B]): IO[A] =
    ioa <* iob // "antes"

  // 3
  def forever[A](io: IO[A]): IO[A] =
    io.flatMap(_ => forever(io))

  // 4
  def convert[A, B](ioa: IO[A], value: B): IO[B] =
    ioa.map(_ => value)
  def convert_v1[A, B](ioa: IO[A], value: B): IO[B] =
    ioa.as(value)
  // 5
  def asUnit[A](ioa: IO[A]): IO[Unit] =
    ioa.as(())
  // 6
  def sum(n: Int): Int =
    if (n <= 0) 0
    else n + sum(n - 1)

  def sumIO(n: Int): IO[Int] =
    if (n < 0) IO(0)
    else
      for {
        ultimo <- IO(n)
        anterior <- IO(n - 1)
      } yield ultimo + anterior

  // 7
  def fibonacci(n: Int): IO[BigInt] =
    if (n < 2) IO(1)
    else
      for {
        ultimo <- IO.delay(fibonacci(n - 1)).flatten
        anterior <- IO.defer(fibonacci(n - 2)) // delay().flatten
      } yield ultimo + anterior

  def main(args: Array[String]): Unit = {
    import cats.effect.unsafe.implicits.global // "platform"
    // "end of the world"
    (1 to 10).foreach(i => println(fibonacci(i).unsafeRunSync()))
  }
}
