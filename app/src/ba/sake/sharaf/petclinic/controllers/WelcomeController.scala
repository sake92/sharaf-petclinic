package ba.sake.sharaf.petclinic.controllers

import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.views.ViewsFactory

class WelcomeController extends PetclinicController {

  override def routes = Routes:
    case GET() -> Path() =>
      Response.withBody(ViewsFactory.welcome)

}
