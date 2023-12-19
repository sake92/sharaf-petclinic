package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*, routing.*

class CrashController() extends PetclinicController:
  override def routes = Routes:
    case GET() -> Path("oups") =>
      throw RuntimeException("Expected: controller used to showcase what happens when an exception is thrown")
