package ba.sake.sharaf.petclinic.web.controllers

import java.time.LocalDate
import io.undertow.util.StatusCodes
import ba.sake.sharaf.utils.*
import ba.sake.sharaf.petclinic.web.models.CreateVisitForm
import ba.sake.sharaf.petclinic.IntegrationTest

class VisitControllerTests extends IntegrationTest {

  val testOwnerId = 1
  val testPetId = 1

  test("Create visit page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Create Visit</h1>"))
  }

  test("Process create visit form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = CreateVisitForm(
      LocalDate.now(),
      "new_visit"
    )

    val res = requests.post(
      s"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
    assert(res.headers("location").head.startsWith("/owners"))
  }

  test("Process create visit form with errors") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = CreateVisitForm(
      LocalDate.now().plusDays(1), // must  not be in future
      "new_visit"
    )

    val res = requests.post(
      s"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.BAD_REQUEST)
  }
}
