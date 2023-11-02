package ba.sake.sharaf.petclinic

import java.util.UUID
import io.undertow.Undertow
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.*
import ba.sake.validson.*
import ba.sake.sharaf.handlers.CorsSettings
import ba.sake.sharaf.*, routing.*, utils.*

@main def main: Unit = {

  val config = ConfigFactory.load().parse[PetclinicConfig]()
  val module = PetclinicModule.of(config)

  module.flyway.migrate()
  module.server.start()
  println(s"Started HTTP server at ${config.baseUrl}")
}
