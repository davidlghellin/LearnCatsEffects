package es.david.cap01.io

import cats.effect.IO

import scala.util.{Failure, Success, Try}
object IOErrorHandlingEjercicios {

  /*
   * Exercises
   */
  // 1 - construct potentially failed IOs from standard data types (Option, Try, Either)
  def option2IO[A](option: Option[A])(ifEmpty: Throwable): IO[A] =
    option match {
      case None => IO.raiseError(ifEmpty)
      case Some(value) => IO.pure(value)
    }
  // IO.fromOption() // equivalente de IO

  def try2IO[A](aTry: Try[A]): IO[A] =
    aTry match {
      case Failure(exception) => IO.raiseError(exception)
      case Success(value) => IO.pure(value)
    }

  def either2IO[A](anEither: Either[Throwable, A]): IO[A] =
    anEither match {
      case Left(ex) => IO.raiseError(ex)
      case Right(value) => IO.pure(value)
    }

  // 2 - handleError, handleErrorWith
  def handleIOError[A](io: IO[A])(handler: Throwable => A): IO[A] =
    io.redeem(ex => handler(ex), value => value)
  def handle2IOError[A](io: IO[A])(handler: Throwable => A): IO[A] =
    io.redeem(handler, identity)

  def handleIOErrorWith[A](io: IO[A])(handler: Throwable => IO[A]): IO[A] =
    io.redeemWith(ex => handler(ex), value => IO.pure(value))

  def handle2IOErrorWith[A](io: IO[A])(handler: Throwable => IO[A]): IO[A] =
    io.redeemWith(handler, IO.pure)

  def main(args: Array[String]): Unit = {}
}
