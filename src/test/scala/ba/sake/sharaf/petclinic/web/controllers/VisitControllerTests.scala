package ba.sake.sharaf.petclinic.web.controllers

import java.time.LocalDate
import sttp.model.StatusCode
import sttp.client4.quick.*
import ba.sake.sharaf.*
import ba.sake.sharaf.petclinic.web.models.CreateVisitForm
import ba.sake.sharaf.petclinic.IntegrationTest

class VisitControllerTests extends IntegrationTest {

  val testOwnerId = 1
  val testPetId = 1

  test("Create visit page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Create Visit</h1>"))
  }

  test("Process create visit form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val reqBody = CreateVisitForm(LocalDate.now(), "new_visit")
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
    assert(res.headers("location").head.startsWith("/owners"))
  }

  test("Process create visit form with errors") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val reqBody = CreateVisitForm(
      LocalDate.now().plusDays(1), // must  not be in future
      "new_visit"
    )
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwnerId}/pets/${testPetId}/visits/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.BadRequest)
  }
}
