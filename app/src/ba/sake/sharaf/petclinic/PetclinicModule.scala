package ba.sake.sharaf.petclinic

import org.flywaydb.core.Flyway
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.hepek.given
import ba.sake.sharaf.exceptions.ExceptionMapper
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.controllers.*
import ba.sake.sharaf.petclinic.web.views.*
import ba.sake.sharaf.undertow.UndertowSharafServer
import sttp.model.StatusCode

case class PetclinicModule(
    config: PetclinicConfig,
    flyway: Flyway,
    server: UndertowSharafServer
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
      val errorPage = ErrorPage(e.getMessage)
      Response.withBody(errorPage).withStatus(StatusCode.InternalServerError)
    }
    val server = UndertowSharafServer(
      "0.0.0.0",
      config.port,
      routes,
      exceptionMapper = customExceptionMapper.orElse(ExceptionMapper.default),
      notFoundHandler = _ => Response.withBody(NotFoundPage).withStatus(StatusCode.NotFound)
    )

    PetclinicModule(config, flyway, server)
  }

}
