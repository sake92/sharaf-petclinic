package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*
import ba.sake.sharaf.hepek.given
import ba.sake.sharaf.petclinic.web.views.*

class WelcomeController extends SharafController:

  override def routes = Routes:
    case GET -> Path() =>
      Response.withBody(WelcomePage)
