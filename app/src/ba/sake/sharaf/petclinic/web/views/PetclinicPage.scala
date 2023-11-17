package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*

trait PetclinicPage extends Page {

  override def siteSettings =
    super.siteSettings.withName("Sharaf Petclinic")

  override def styleURLs: List[String] = super.styleURLs ++ List(
    "/styles/main.css"
  )

  override def bodyContent: Frag = frag(
    Navbar.full(
      brandUrl = "/",
      brandName = Some("PetClinic"),
      left = Seq(
        a(href := "/owners", cls := "nav-link")("Owners") -> Seq.empty,
        a(href := "/vets", cls := "nav-link")("Veterinarians") -> Seq.empty
      )
    ),
    div(bootstrapContainer)(
      pageContent
    )
  )

}
