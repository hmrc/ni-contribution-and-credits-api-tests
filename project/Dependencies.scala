import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "com.typesafe"         % "config"                  % "1.4.9"   % Test,
    "com.typesafe.play"   %% "play-ahc-ws-standalone"  % "2.2.16"  % Test,
    "com.typesafe.play"   %% "play-ws-standalone-json" % "2.2.16"  % Test,
    "uk.gov.hmrc"         %% "api-test-runner"         % "0.10.0"  % Test,
    "com.vladsch.flexmark" % "flexmark-all"            % "0.64.8"  % Test,
    "org.scalatest"       %% "scalatest"               % "3.2.20"  % Test,
    "org.slf4j"            % "slf4j-simple"            % "2.0.18"  % Test,
    "com.google.inject"    % "guice"                   % "7.0.0"   % Test,
    "io.circe"            %% "circe-core"              % "0.14.15" % Test,
    "io.circe"            %% "circe-generic"           % "0.14.15" % Test,
    "io.circe"            %% "circe-parser"            % "0.14.15" % Test,
    "com.typesafe.play"   %% "play-json"               % "2.10.8"  % Test
  )

}
