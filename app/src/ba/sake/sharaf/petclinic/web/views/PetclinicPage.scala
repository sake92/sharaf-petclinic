package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

trait PetclinicPage extends Page {

  override def siteSettings =
    super.siteSettings.withName("Sharaf Petclinic")

  override def styleURLs: List[String] = super.styleURLs ++ List(
    "/styles/main.css"
  )

  override def bodyContent: Frag = frag(
    Navbar.nav(
      brandUrl = "/",
      brandName = Some("PetClinic"),
      left = Seq(
        Navbar.link("/owners/find", "Find Owners"),
        Navbar.link("/vets", "Veterinarians"),
        Navbar.link("/oups", "Error")
      )
    ),
    div(cls := "container")(
      pageContent
    )
  )

}
