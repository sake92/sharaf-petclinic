package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.web.views.*

class WelcomeController extends PetclinicController:

  override def routes = Routes:
    case GET() -> Path() =>
      Response.withBody(WelcomePage())
