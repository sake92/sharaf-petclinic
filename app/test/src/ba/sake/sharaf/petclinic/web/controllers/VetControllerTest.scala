package ba.sake.sharaf.petclinic.web.controllers

import sttp.model.StatusCode
import sttp.client4.quick.*
import ba.sake.sharaf.petclinic.IntegrationTest

class VetControllerTest extends IntegrationTest {
  test("Veterinarians first page") {
    val res = quickRequest.get(uri"${itFixture().config.baseUrl}/vets").send()
    assertEquals(res.code, StatusCode.Ok)
    assert(res.body.contains("<h1>Veterinarians</h1>"))
  }

  test("Invalid page displays an HTML error") {
    val res = quickRequest.get(uri"${itFixture().config.baseUrl}/vets?number=-1").send()
    assertEquals(res.code, StatusCode.UnprocessableEntity)
    assert(res.body.contains("<h1>Something happened...</h1>"))
  }
}
