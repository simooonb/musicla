name := "musicla"

version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)

resolvers += Resolver.bintrayRepo("writethemfirst", "maven")

libraryDependencies ++= Seq(
  "com.colisweb"   %% "approvals-scala"          % "1.3.0",
  "io.circe"                    %% "circe-parser"             % "0.13.0",
  "io.circe"                    %% "circe-core"               % "0.13.0",
  "io.circe"                    %% "circe-generic"            % "0.13.0",
  "io.circe"                    %% "circe-refined"            % "0.13.0",
  "io.circe"                    %% "circe-generic-extras"     % "0.13.0",
  "io.circe"                    %% "circe-literal"            % "0.13.0",
  "net.logstash.logback"         % "logstash-logback-encoder" % "6.6",
  "io.chrisdavenport"           %% "log4cats-core"            % "1.0.1",
  "io.chrisdavenport"           %% "log4cats-slf4j"           % "1.0.1",
  "org.typelevel"               %% "kind-projector"           % "0.10.3",
  "com.lihaoyi"                 %% "pprint"                   % "0.6.2",
  "com.lihaoyi"                 %% "requests"                 % "0.6.5",
  "com.github.pureconfig"       %% "pureconfig"               % "0.14.1",
  "com.github.pureconfig"       %% "pureconfig-cats-effect"   % "0.14.1",
  "eu.timepit"                  %% "refined-pureconfig"       % "0.9.21",
  "eu.timepit"                  %% "refined"                  % "0.9.21",
  "ch.qos.logback"               % "logback-classic"          % "1.2.3",
  "com.typesafe.scala-logging"  %% "scala-logging"            % "3.9.2",
  "org.http4s"                  %% "http4s-dsl"               % "0.21.0",
  "org.http4s"                  %% "http4s-circe"             % "0.21.0",
  "org.http4s"                  %% "http4s-jetty"             % "0.21.0",
  "org.scalatest"               %% "scalatest"                % "3.2.5",
  "org.scalacheck"              %% "scalacheck"               % "1.15.3",
  "org.scalatestplus"           %% "scalatestplus-scalacheck" % "3.1.0.0-RC2",
  "com.softwaremill.sttp.tapir" %% "tapir-core"               % "0.17.19",
  "com.softwaremill.sttp.tapir" %% "tapir-zio"                % "0.17.19",
  "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server"  % "0.17.19",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"         % "0.17.19"
)
