package ba.sake.sharaf.petclinic

import io.undertow.Undertow
import io.undertow.util.StatusCodes
import org.flywaydb.core.Flyway
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.handlers.ErrorMapper
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.controllers.*
import ba.sake.sharaf.petclinic.web.views.ViewsFactory

case class PetclinicModule(
    config: PetclinicConfig,
    flyway: Flyway,
    server: Undertow
)

object PetclinicModule {

  def apply(config: PetclinicConfig): PetclinicModule = {
    // db
    val ds = com.zaxxer.hikari.HikariDataSource()
    ds.setJdbcUrl(config.db.jdbcUrl)
    ds.setUsername(config.db.username)
    ds.setPassword(config.db.password)

    val flyway = Flyway.configure().dataSource(ds).schemas("petclinic").load()

    val squeryContext = SqueryContext(ds)
    val vetDao = VetDao(squeryContext)
    val ownerDao = OwnerDao(squeryContext)
    val petDao = PetDao(squeryContext)

    // services / domain
    val vetService = VetService(vetDao)
    val ownerService = OwnerService(ownerDao)
    val petService = PetService(petDao)

    // web
    val controllers =
      Seq(
        WelcomeController(),
        VetController(vetService),
        OwnerController(ownerService),
        PetController(ownerService, petService),
        VisitController(petService),
        CrashController()
      )
    val routes: Routes = Routes.merge(controllers.map(_.routes))

    val customErrorMapper: ErrorMapper = { case e: RuntimeException =>
      val errorPage = ViewsFactory.error(e.getMessage())
      Response.withBody(errorPage).withStatus(StatusCodes.INTERNAL_SERVER_ERROR)
    }
    val httpHandler = SharafHandler(routes)
      .withErrorMapper(customErrorMapper.orElse(ErrorMapper.default))
      .withNotFoundHandler { _ =>
        val errorPage = ViewsFactory.notFound
        Response.withBody(errorPage).withStatus(StatusCodes.NOT_FOUND)
      }

    val server = Undertow
      .builder()
      .addHttpListener(config.port, "0.0.0.0", httpHandler)
      .build()

    PetclinicModule(config, flyway, server)
  }

}
