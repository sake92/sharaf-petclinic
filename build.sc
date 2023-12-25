import mill._, scalalib._, scalafmt._

import coursier.maven.MavenRepository

object app extends ScalaModule with ScalafmtModule {

  def mainClass = T(Some("ba.sake.sharaf.petclinic.main"))

  def scalaVersion = "3.3.1"

  def scalacOptions = super.scalacOptions() ++ Seq(
    "-Yretain-trees",
    "-deprecation",
    "-Wunused:all",
    "-explain"
  )

  def ivyDeps = Agg(
    ivy"ba.sake::sharaf:0.0.18",
    // db
    ivy"ba.sake::squery:0.0.16",
    ivy"org.flywaydb:flyway-core:8.5.5",
    ivy"com.zaxxer:HikariCP:5.0.1",
    ivy"org.postgresql:postgresql:42.6.0",
    // other
    ivy"io.scalaland::chimney::0.8.4",
    ivy"ch.qos.logback:logback-classic:1.4.6"
  )

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::0.7.29",
      ivy"org.testcontainers:postgresql:1.17.6"
    )
  }
}
