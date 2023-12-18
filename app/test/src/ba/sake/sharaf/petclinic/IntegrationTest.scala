package ba.sake.sharaf.petclinic

import org.testcontainers.containers.PostgreSQLContainer
import com.typesafe.config.ConfigFactory
import ba.sake.tupson.config.*
import ba.sake.sharaf.utils.*

trait IntegrationTest extends munit.FunSuite {

  protected val itFixture = new Fixture[PetclinicModule]("PetclinicModule") {

    private var pgContainer: PostgreSQLContainer[?] = _

    private var module: PetclinicModule = _

    def apply() = module

    override def beforeAll(): Unit =

      // start postgres test container
      pgContainer = PostgreSQLContainer("postgres:14")
      pgContainer = pgContainer.withDatabaseName("petclinic")
      pgContainer = pgContainer.withUsername("petclinic")
      pgContainer = pgContainer.withPassword("petclinic_test")
      pgContainer.start()

      val dbConfig = DatabaseConfig(pgContainer.getJdbcUrl(), pgContainer.getUsername(), pgContainer.getPassword())
      val port = getFreePort()

      val config = ConfigFactory
        .load()
        .parseConfig[PetclinicConfig]
        .copy(port = port, baseUrl = s"http://localhost:$port", db = dbConfig)

      // assign fixture
      module = PetclinicModule(config)

      module.flyway.migrate()
      module.server.start()

    override def afterAll(): Unit =
      module.server.stop()
      pgContainer.stop()
  }

  override def munitFixtures = List(itFixture)
}
