package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

class ErrorPage(message: String) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Welcome!")

  // you shouldn't do this in a real app,
  // you shouldn't leak (all) exception stacktraces
  override def pageContent: Frag = div(
    h1("Something happened..."),
    hr,
    h2("Stacktrace:"),
    div(message)
  )

}
