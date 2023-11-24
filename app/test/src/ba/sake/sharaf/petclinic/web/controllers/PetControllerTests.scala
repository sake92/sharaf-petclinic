package ba.sake.sharaf.petclinic.web.controllers

import java.time.LocalDate
import io.undertow.util.StatusCodes
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

    val res = requests.get(s"${baseUrl}/owners/${testOwnerId}/pets/new")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Create Pet</h1>"))
  }

  test("Process create pet form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = UpsertPetForm(
      "new_name",
      LocalDate.parse("2000-09-07"),
      PetType.dog
    )

    val res = requests.post(
      s"${baseUrl}/owners/${testOwnerId}/pets/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
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

    val res = requests.post(
      s"${baseUrl}/owners/${testOwnerId}/pets/new",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.BAD_REQUEST)
  }

  /* UPDATE */
  test("Update pet page") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val res = requests.get(s"${baseUrl}/owners/${testOwnerId}/pets/${testPet.id}/edit")
    assertEquals(res.statusCode, StatusCodes.OK)
    assert(res.text().contains("<h1>Edit Pet</h1>"))
  }

  test("Process update pet form") {
    val fixture = itFixture()
    val baseUrl = fixture.config.baseUrl

    val reqBody = UpsertPetForm(
      "name",
      LocalDate.parse("2000-09-07"),
      PetType.dog
    )

    val res = requests.post(
      s"${baseUrl}/owners/${testOwnerId}/pets/${testPet.id}/edit",
      data = reqBody.toRequestsMultipart(),
      check = false, // dont fail on non 2xx code
      maxRedirects = 0 // dont follow redirect
    )
    assertEquals(res.statusCode, StatusCodes.MOVED_PERMANENTLY)
    assert(res.headers("location").head.startsWith("/owners"))
  }

}
