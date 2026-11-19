package ba.sake.sharaf.petclinic

import scala.compiletime.uninitialized
import org.testcontainers.postgresql.PostgreSQLContainer
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*

trait IntegrationTest extends munit.FunSuite {

  protected val itFixture = new Fixture[PetclinicModule]("PetclinicModule") {

    private var pgContainer: PostgreSQLContainer = uninitialized

    private var module: PetclinicModule = uninitialized

    def apply() = module

    override def beforeAll(): Unit =
      pgContainer = PostgreSQLContainer("postgres:14")
      pgContainer = pgContainer
        .withDatabaseName("petclinic")
        .withUsername("petclinic")
        .withPassword("petclinic_test")
      pgContainer.start()

      val dbConfig = DatabaseConfig(pgContainer.getJdbcUrl, pgContainer.getUsername, pgContainer.getPassword)
      val socket = new java.net.ServerSocket(0)
      val port = try socket.getLocalPort finally socket.close()
      val config = ConfigFactory
        .load()
        .parseConfig[PetclinicConfig]
        .copy(port = port, baseUrl = s"http://localhost:$port", db = dbConfig)
      module = PetclinicModule(config)
      module.flyway.migrate()
      module.server.start()

    override def afterAll(): Unit =
      module.server.stop()
      pgContainer.stop()
  }

  override def munitFixtures = List(itFixture)
}
