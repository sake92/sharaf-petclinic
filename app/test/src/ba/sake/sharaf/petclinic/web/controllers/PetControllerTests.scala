package ba.sake.sharaf.petclinic.web.controllers

import java.time.LocalDate
import sttp.model.StatusCode
import sttp.client4.quick.*
import ba.sake.sharaf.*
import ba.sake.sharaf.utils.*
import ba.sake.sharaf.petclinic.common.PetType
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.web.models.UpsertPetForm
import ba.sake.sharaf.petclinic.IntegrationTest

class PetControllerTests extends IntegrationTest {

  val testOwnerId = 1
  val testPet = Pet(
    id = 1,
    name = "Leo",
    birthDate = LocalDate.parse("2000-09-07"),
    petType = PetType.cat,
    visits = Seq.empty
  )

  /* CREATE */
  test("Create pet page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/${testOwnerId}/pets/new").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Create Pet</h1>"))
  }

  test("Process create pet form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val reqBody = UpsertPetForm(
      "new_name",
      LocalDate.parse("2000-09-07"),
      PetType.dog
    )
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwnerId}/pets/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
    assert(res.headers("location").head.startsWith("/owners"))
  }

  test("Process create pet form with errors") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val reqBody = UpsertPetForm(
      "name",
      LocalDate.now(), // must be in past!
      PetType.dog
    )
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwnerId}/pets/new")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.BadRequest)
  }

  /* UPDATE */
  test("Update pet page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val res = quickRequest.get(uri"${baseUrl}/owners/${testOwnerId}/pets/${testPet.id}/edit").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Edit Pet</h1>"))
  }

  test("Process update pet form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl
    val reqBody = UpsertPetForm(
      "name",
      LocalDate.parse("2000-09-07"),
      PetType.dog
    )
    val res = quickRequest
      .post(uri"${baseUrl}/owners/${testOwnerId}/pets/${testPet.id}/edit")
      .multipartBody(reqBody.toSttpMultipart())
      .followRedirects(false)
      .send()
    assertEquals(res.code, StatusCode.MovedPermanently)
    assert(res.headers("location").head.startsWith("/owners"))
  }

}
