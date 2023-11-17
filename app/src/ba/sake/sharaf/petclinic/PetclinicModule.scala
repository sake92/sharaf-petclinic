package ba.sake.sharaf.petclinic

import io.undertow.Undertow
import org.flywaydb.core.Flyway
import ba.sake.sharaf.*
import ba.sake.sharaf.handlers.*
import ba.sake.sharaf.routing.*
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.petclinic.controllers.*

case class PetclinicModule(
    config: PetclinicConfig,
    flyway: Flyway,
    // services: Services,
    server: Undertow
)

object PetclinicModule {

  def of(config: PetclinicConfig): PetclinicModule = {

    val ds = com.zaxxer.hikari.HikariDataSource()
    ds.setJdbcUrl(config.db.jdbcUrl)
    ds.setUsername(config.db.username)
    ds.setPassword(config.db.password)

    val flyway = Flyway.configure().dataSource(ds).schemas("petclinic").load()

    val controllers = Seq(WelcomeController())
    val routes: Routes = Routes.merge(controllers.map(_.routes))
    val httpHandler = SharafHandler(routes)

    val server = Undertow
      .builder()
      .addHttpListener(config.port, "0.0.0.0", httpHandler)
      .build()

    PetclinicModule(config, flyway, server)
  }

}
