package ba.sake.sharaf.petclinic

import io.undertow.Undertow
import io.undertow.util.StatusCodes
import org.flywaydb.core.Flyway
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.exceptions.ExceptionMapper
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.controllers.*
import ba.sake.sharaf.petclinic.web.views.*

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
    val vetDao = VetDao()
    val ownerDao = OwnerDao()
    val petDao = PetDao()

    // services / domain
    val vetService = VetService(squeryContext, vetDao)
    val ownerService = OwnerService(squeryContext, ownerDao)
    val petService = PetService(squeryContext, petDao)

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
    val routes = Routes.merge(controllers.map(_.routes))

    val customExceptionMapper: ExceptionMapper = { case e: RuntimeException =>
      val errorPage = ErrorPage(e.getMessage())
      Response.withBody(errorPage).withStatus(StatusCodes.INTERNAL_SERVER_ERROR)
    }
    val httpHandler = SharafHandler(routes)
      .withExceptionMapper(customExceptionMapper.orElse(ExceptionMapper.default))
      .withNotFoundHandler { _ =>
        Response.withBody(NotFoundPage).withStatus(StatusCodes.NOT_FOUND)
      }

    val server = Undertow
      .builder()
      .addHttpListener(config.port, "0.0.0.0", httpHandler)
      .build()

    PetclinicModule(config, flyway, server)
  }

}
