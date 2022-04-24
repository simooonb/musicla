name := "musicla"

version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

resolvers += Resolver.bintrayRepo("writethemfirst", "maven")

libraryDependencies ++= Seq(
  "com.colisweb"                %% "approvals-scala"          % "1.3.0",
  "io.circe"                    %% "circe-parser"             % "0.14.1",
  "io.circe"                    %% "circe-core"               % "0.14.1",
  "io.circe"                    %% "circe-generic"            % "0.14.1",
  "io.circe"                    %% "circe-refined"            % "0.14.1",
  "io.circe"                    %% "circe-generic-extras"     % "0.14.1",
  "io.circe"                    %% "circe-literal"            % "0.14.1",
  "net.logstash.logback"         % "logstash-logback-encoder" % "7.0.1",
  "io.chrisdavenport"           %% "log4cats-core"            % "1.1.1",
  "io.chrisdavenport"           %% "log4cats-slf4j"           % "1.1.1",
  "com.lihaoyi"                 %% "pprint"                   % "0.7.3",
  "com.lihaoyi"                 %% "requests"                 % "0.7.0",
  "com.github.pureconfig"       %% "pureconfig"               % "0.17.1",
  "com.github.pureconfig"       %% "pureconfig-cats-effect"   % "0.14.1",
  "eu.timepit"                  %% "refined-pureconfig"       % "0.9.28",
  "eu.timepit"                  %% "refined"                  % "0.9.28",
  "ch.qos.logback"               % "logback-classic"          % "1.2.11",
  "com.typesafe.scala-logging"  %% "scala-logging"            % "3.9.4",
  "org.http4s"                  %% "http4s-dsl"               % "0.21.0",
  "org.http4s"                  %% "http4s-circe"             % "0.21.0",
  "org.http4s"                  %% "http4s-jetty"             % "0.21.0",
  "org.scalatest"               %% "scalatest"                % "3.2.5",
  "org.scalacheck"              %% "scalacheck"               % "1.15.4",
  "org.scalatestplus"           %% "scalatestplus-scalacheck" % "3.1.0.0-RC2",
  "com.softwaremill.sttp.tapir" %% "tapir-core"               % "0.17.19",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % "0.17.19",
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"      % "0.17.19"
)

Compile / mainClass := Some("bar.simon.learn.music.Main")

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

dockerBaseImage := "adoptopenjdk/openjdk14:jre-14.0.2_12"
