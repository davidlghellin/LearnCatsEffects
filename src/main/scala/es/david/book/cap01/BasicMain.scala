package es.david.book.cap01

import cats.implicits._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object BasicMain extends App {
  val twice =
    Future(println("Hello World!"))
      .flatMap(_ => Future(println("Hello World!")))

  Thread.sleep(1000) // ponemos el sleep para poder ver el futuro
}
