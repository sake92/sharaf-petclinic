package ba.sake.sharaf.petclinic

import io.undertow.server.HttpHandler
import io.undertow.server.session.{InMemorySessionManager, SessionAttachmentHandler, SessionCookieConfig}
import io.undertow.{Handlers, Undertow}
import org.flywaydb.core.Flyway
import ba.sake.hepek.html.HtmlPage
import ba.sake.sharaf.*
import ba.sake.sharaf.handlers.*
import ba.sake.sharaf.routing.*
import ba.sake.squery.SqueryContext

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

    val flyway: Flyway = Flyway.configure().dataSource(ds).schemas("petclinic").load()

    val httpHandler: HttpHandler = locally {

      val routes: Routes = { case _ =>
        Response.withBody("opppaaaaaaaaaa")
      }
      SharafHandler(routes)
    }

    val server = Undertow
      .builder()
      .addHttpListener(config.port, "0.0.0.0", httpHandler)
      .build()

    PetclinicModule(config, flyway, server)
  }

}
