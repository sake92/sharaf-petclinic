package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*

class CrashController() extends SharafController:
  override def routes: Routes = Routes:
    case GET -> Path("oups") =>
      throw RuntimeException("Expected: controller used to showcase what happens when an exception is thrown")
