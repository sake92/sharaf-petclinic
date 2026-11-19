package ba.sake.sharaf.petclinic.web.controllers

import sttp.model.StatusCode
import sttp.client4.quick.*
import ba.sake.sharaf.petclinic.IntegrationTest

class VetControllerTest extends IntegrationTest {
  test("Static assets and missing pages") {
    val css = quickRequest.get(uri"${itFixture().config.baseUrl}/styles/main.css").send()
    assertEquals(css.code, StatusCode.Ok)
    assert(css.body.nonEmpty)

    val missing = quickRequest.get(uri"${itFixture().config.baseUrl}/missing-page").send()
    assertEquals(missing.code, StatusCode.NotFound)
    assert(missing.body.contains("<h1>Not Found...</h1>"))
  }

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
