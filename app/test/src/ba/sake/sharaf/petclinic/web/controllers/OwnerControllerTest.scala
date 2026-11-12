package ba.sake.sharaf.petclinic.web.controllers

import sttp.model.StatusCode
import sttp.client4.quick.*
import ba.sake.sharaf.*
import ba.sake.sharaf.petclinic.domain.models.Owner
import ba.sake.sharaf.petclinic.web.models.UpsertOwnerForm
import ba.sake.sharaf.petclinic.IntegrationTest

class OwnerControllerTests extends IntegrationTest {

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
    val res = quickRequest.get(uri"${baseUrl}/owners/find").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Find Owners</h1>"))
  }

  test("Find owners result page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Owners</h1>"))
  }

  test("Find owners by last name (single owner found)") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest
      .get(uri"${baseUrl}/owners?q=Franklin")
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
    assertEquals(res.headers("location").head, "/owners/1")
  }

  test("Show owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/${testOwner.id}").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Owner details</h1>"))
  }

  /* CREATE */
  test("Create owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/new").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Create Owner</h1>"))
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
    val res = quickRequest
      .post(uri"${baseUrl}/owners/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
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
    val res = quickRequest
      .post(uri"${baseUrl}/owners/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.BadRequest)
  }

  /* UPDATE */
  test("Update owner page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/${testOwner.id}/edit").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Edit Owner</h1>"))
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
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwner.id}/edit")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
    assert(res.header("location").get.startsWith("/owners"), "Expected redirect to owner page")
  }

}
