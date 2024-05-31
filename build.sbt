lazy val root = (project in file("."))
  .settings(
    name := "bereavement-support-payment-api-tests",
    version := "0.1.0",
    scalaVersion := "2.13.12",
    libraryDependencies ++= Dependencies.test,

  )
