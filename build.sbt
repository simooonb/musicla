name := "learn-music"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"                   % "3.2.5",
  "org.scalacheck"    %% "scalacheck"                  % "1.15.3",
  "org.scalatestplus" %% "scalatestplus-scalacheck"    % "3.1.0.0-RC2"
).map(_ % Test)
