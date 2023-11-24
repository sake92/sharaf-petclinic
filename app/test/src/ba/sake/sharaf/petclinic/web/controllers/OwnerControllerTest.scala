package ba.sake.sharaf.petclinic.web.controllers

import io.undertow.util.StatusCodes
import ba.sake.sharaf.utils.*
import ba.sake.sharaf.petclinic.domain.models.Owner
import ba.sake.sharaf.petclinic.web.models.UpsertOwnerForm
import ba.sake.sharaf.petclinic.IntegrationTest

class ProgramControllerTests extends IntegrationTest {

  val testOwner = Owner(
    1,
    firstName = "George",
    lastName = "Franklin",
    address = "110 W. Liberty St.",
    city = "Madison",
    telephone = "6085551023",
    pets = Seq.empty
  )

  test("Find owners page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/find")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Find Owners</h1>"))
  }

  test("Find owners result page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Owners</h1>"))
  }

  test("Find owners by last name (single owner found)") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(
      s"${baseUrl}/owners?q=Franklin",
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
    assertEquals(res.headers("location").head, "/owners/1")
  }

  test("Show owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/${testOwner.id}")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Owner details</h1>"))
  }

  /* CREATE */
  test("Create owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/new")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Create Owner</h1>"))
  }

  test("Process create owner form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = UpsertOwnerForm(
      "firstName",
      "lastName",
      "address",
      "city",
      "1234567890"
    )

    val res = requests.post(
      s"${baseUrl}/owners/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
    assert(res.headers("location").head.startsWith("/owners"))
  }

  test("Process create owner form with errors") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = UpsertOwnerForm(
      " ", // firstName mustn't be blank
      "lastName",
      "address",
      "city",
      "123456789" // one digit missing
    )

    val res = requests.post(
      s"${baseUrl}/owners/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.BAD_REQUEST)
  }

  /* UPDATE */
  test("Update owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/${testOwner.id}/edit")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Edit Owner</h1>"))
  }

  test("Process update owner form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = UpsertOwnerForm(
      "firstName",
      "lastName",
      "address",
      "city",
      "1234567890"
    )

    val res = requests.post(
      s"${baseUrl}/owners/${testOwner.id}/edit",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
    assert(res.headers("location").head.startsWith("/owners"))
  }

}
