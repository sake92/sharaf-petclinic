scalaVersion := "3.9.0"

lazy val root = project.in(file("."))
  .settings(
    Compile / mainClass := Some("ba.sake.sharaf.petclinic.main"),
    scalacOptions ++= Seq("-Yretain-trees", "-deprecation", "-Wunused:all"),
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-potateh" % "0.24.0",
      "ba.sake" %% "sharaf-hepek-components" % "0.24.0",
      "ba.sake" %% "tupson-config" % "0.32.1",
      "ba.sake" %% "squery" % "0.13.0",
      "org.flywaydb" % "flyway-core" % "13.12.1",
      "org.flywaydb" % "flyway-database-postgresql" % "13.12.1",
      "com.zaxxer" % "HikariCP" % "7.1.0",
      "org.postgresql" % "postgresql" % "42.7.14",
      "io.scalaland" %% "chimney" % "2.1.0",
      "ch.qos.logback" % "logback-classic" % "1.6.6",
      "org.scalameta" %% "munit" % "1.3.6" % Test,
      "org.testcontainers" % "testcontainers-postgresql" % "2.0.5" % Test
    )
  )
