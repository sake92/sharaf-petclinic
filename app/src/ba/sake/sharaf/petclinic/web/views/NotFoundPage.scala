package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

object NotFoundPage extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Not Found")

  override def pageContent: Frag = div(
    h1("Not Found..."),
    hr,
    h2("¯\\_(ツ)_/¯")
  )

}
