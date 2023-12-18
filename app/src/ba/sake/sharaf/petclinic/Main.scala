package ba.sake.sharaf.petclinic

import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*
import ba.sake.sharaf.*

@main def main: Unit =

  val config = ConfigFactory.load().parseConfig[PetclinicConfig]
  val module = PetclinicModule(config)

  module.flyway.migrate()
  module.server.start()

  println(s"Started HTTP server at ${config.baseUrl}")
