import cats.effect.IOApp
import cats.effect.IO


object Playground extends IOApp.Simple {
  override def run: IO[Unit] = 
    IO.println("Aprendiendo Cats effects")
}
