package es.david.cap01.io

import scala.io.StdIn

object EffectsEjercicios {
  /*
    example: MyIO data type from the Monads lesson - it IS an effect type
    - describes any computation that might produce side effects
    - calculates a value of type A, if it's successful
    - side effects are required for the evaluation of () => A
      - YES, the creation of MyIO does NOT produce the side effects on construction
   */
  case class MyIO[A](unsafeRun: () => A) {
    def map[B](f: A => B): MyIO[B] =
      MyIO(() => f(unsafeRun()))

    def flatMap[B](f: A => MyIO[B]): MyIO[B] =
      MyIO(() => f(unsafeRun()).unsafeRun())
  }

  val anIO: MyIO[Int] = MyIO { () =>
    println("I'm writing something...")
    42
  }

  /**  Exercises
    *  1. An IO which returns the current time of the system
    *  2. An IO which measures the duration of a computation (hint: use ex 1)
    *  3. An IO which prints something to the console
    *  4. An IO which reads a line (a string) from the std input
    */

  // 1
  // crearemos un MyIO que le pasaremos un lambda que devuelva el tiempo
  val relog: MyIO[Long] = MyIO(() => System.currentTimeMillis())

  // 2
  def calComputo[A](computation: MyIO[A]): MyIO[Long] = for {
    startTime <- relog
    _ <- computation
    finTime <- relog
  } yield finTime - startTime

  // 3
  // crearemos un MyIO que pasando el string lo imprima en pantalla
  def printConsole(str: String): MyIO[Unit] = MyIO(() => println(str))

  // 4
  // crearemos un MyIO que lea de la entrada standar la línea
  def lecturaStdin: MyIO[String] = MyIO(() => StdIn.readLine())

  def testConsole(): Unit = {
    val program: MyIO[Unit] = for {
      linea1 <- lecturaStdin
      linea2 <- lecturaStdin
      _ <- printConsole(linea1 + linea2)
    } yield ()

    program.unsafeRun()
  }

  def main(args: Array[String]): Unit =
    testConsole()
}
