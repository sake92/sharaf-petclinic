package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

class ErrorPage(message: String) extends PetclinicPage:

  override def pageSettings = super.pageSettings
    .withTitle("An error happened :/")

  override def pageContent: Frag = div(
    h1("Something happened..."),
    p(message)
  )
