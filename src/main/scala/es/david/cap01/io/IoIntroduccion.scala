package es.david.cap01.io

import cats.effect.IO
import scala.io.StdIn

object IoIntroduccion {
  // IO
  val nuestroPrimerIO: IO[Int] =
    IO.pure(32) // pure solamente lo mete dentro, no debe tener efectos de lado el argumento
  val unDelayedIO: IO[Int] = IO.delay {
    println("Produzco un entero")
    52
  }
  val unDelayedIO_v2: IO[Int] = IO { // delay == apply
    println("Produzco un entero")
    52
  }

  def smallProgram: IO[Unit] = for {
    linea1 <- IO(StdIn.readLine())
    linea2 <- IO(StdIn.readLine())
    _ <- IO.delay(println(linea1 + linea2))
  } yield ()

  val ourFirstIO: IO[Int] = IO.pure(42) // arg that should not have side effects

  // map, flatMap
  val improvedMeaningOfLife = ourFirstIO.map(_ * 2)
  val printedMeaningOfLife = ourFirstIO.flatMap(mol => IO.delay(println(mol)))
  // mapN - combine IO effects as tuples
  import cats.syntax.apply._
  val combinedMeaningOfLife: IO[Int] = (ourFirstIO, improvedMeaningOfLife).mapN(_ + _)
  def smallProgram_v2(): IO[Unit] =
    (IO(StdIn.readLine()), IO(StdIn.readLine())).mapN(_ + _).map(println)

  def main(args: Array[String]): Unit = {
    import cats.effect.unsafe.implicits.global
    println(unDelayedIO.unsafeRunSync())
    smallProgram.unsafeRunSync()

  }
}
