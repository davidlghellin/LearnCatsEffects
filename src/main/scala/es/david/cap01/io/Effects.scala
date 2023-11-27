package es.david.cap01.io

import scala.concurrent.Future
import scala.io.StdIn

object Effects {

  // Programación funcional pura
  // substitución
  def combine(a: Int, b: Int): Int = a + b
  val five = combine(2, 3)
  val five_v2 = 2 + 3
  val five_v3 = 5

  // Transaparencia referencial
  // ejemplos no
  val printAlgo = println("Cats effects")
  val printAlgo_v2 = () // no es lo mismo

  var unInt = 0
  val cambiamosInt: Unit = (unInt += 1)
  val cambiamosInt_v2 = () // no es lo mismo

  // los efectos de lado son inevitables por el uso de los programas

  /** ******************************
    */
  // effect
  /** ******************************
    */

  /*
    Effect types
    Properties:
    - type signature describes the kind of calculation that will be performed
    - type signature describes the VALUE that will be calculated
    - when side effects are needed, effect construction is separate from effect execution
   */

  // option
  /*
    example: Option is an effect type
    - describes a possibly absent value
    - computes a value of type A, if it exists
    - side effects are not needed
   */
  val unOption: Option[Int] = Option(54) // un option describe un posible ausencia de valor

  // futuro
  /*
    example: Future is NOT an effect type
    - describes an asynchronous computation
    - computes a value of type A, if it's successful
    - side effect is required (allocating/scheduling a thread), execution is NOT separate from construction
   */
  import scala.concurrent.ExecutionContext.Implicits.global
  val aFuture: Future[Int] = Future(42)

  def main(args: Array[String]): Unit = {}

}
