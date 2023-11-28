package es.david.cap01.io

import cats.effect.IO
import scala.io.StdIn

object IOErrorHandling {

  // IO: pure, delay, defer
  // creamos un efetcto fallido
  val unFalloCompu: IO[Int] = IO.delay(throw new RuntimeException("Un fallo!!"))
  val unFallo: IO[Int] = IO.raiseError(new RuntimeException("falla una proper"))

  // handle exceptions
  val dealWithIt = unFallo.handleErrorWith {
    case _: ArrayIndexOutOfBoundsException =>
      IO.delay(println("Estamos fuera del indice, hacemos otras cosas"))
    case _: RuntimeException => IO.delay(println("Estamos aqui"))
    // añadir más
  }

  /* attempt
   * Materializes any sequenced exceptions into value space, where
   * they may be handled.
   *
   * This is analogous to the `catch` clause in `try`/`catch`, being
   * the inverse of `IO.raiseError`. Thus:
   *
   * {{{
   * IO.raiseError(ex).attempt.unsafeRunAsync === Left(ex)
   * }}}
   *
   * @see [[IO.raiseError]]
   */
  val effectsComoEither: IO[Either[Throwable, Int]] = unFallo.attempt

  // redeem
  val resultAsString: IO[String] = unFallo.redeem(ex => s"Fallo: $ex", value => s"SUCCES: $value")
  val resultAsEffects: IO[Unit] = unFallo.redeemWith(ex => IO(println(s"Fallo: $ex")), value => IO(println(s"SUCCES: $value")))

  def main(args: Array[String]): Unit = {
    import cats.effect.unsafe.implicits.global
    // unFalloCompu.unsafeRunSync()
    // println(resultAsString.unsafeRunSync())
    resultAsEffects.unsafeRunSync()
  }
}
