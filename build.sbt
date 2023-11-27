val scala3Version = "2.12.18"

lazy val root = project
  .in(file("."))
  .settings(
    name := "LearnCatsEffects",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.2.0",
    )
  )
