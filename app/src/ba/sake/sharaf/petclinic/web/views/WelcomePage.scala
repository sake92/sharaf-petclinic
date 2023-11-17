package ba.sake.sharaf.petclinic.web.views

import Bundle.Tags.*

class WelcomePage extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Welcome!")

  override def pageContent: Frag =
    "Welcome!"

}
