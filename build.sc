import mill._, scalalib._, scalafmt._

import coursier.maven.MavenRepository

object app extends ScalaModule with ScalafmtModule {

  def mainClass = T(Some("ba.sake.sharaf.petclinic.main"))

  // TODO remove once done
  def repositoriesTask = T.task {
    super.repositoriesTask() ++ Seq(MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"))
  }

  def scalaVersion = "3.3.1"

  def scalacOptions = super.scalacOptions() ++ Seq(
    "-Yretain-trees",
    "-deprecation",
    "-Wunused:all",
    "-explain"
  )

  def ivyDeps = Agg(
    ivy"ba.sake::sharaf:0.0.13",
    ivy"ba.sake::hepek-components:0.15.0+2-893fd286+20231120-1149-SNAPSHOT", // TODO remove when updated sharaf
    // db
    ivy"org.postgresql:postgresql:42.6.0",
    ivy"com.zaxxer:HikariCP:5.0.1",
    ivy"org.flywaydb:flyway-core:8.5.5",
    ivy"ba.sake::squery:0.0.11",
    // other
    ivy"ch.qos.logback:logback-classic:1.4.6"
  )

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::0.7.29"
    )
  }
}
