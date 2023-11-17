package ba.sake.sharaf.petclinic.views

import Bundle.*, Tags.*

trait PetclinicPage extends Bundle.Page {

  override def styleURLs: List[String] = super.styleURLs ++ List(
    "/styles/main.css"
  )

  override def bodyContent: Frag = frag(
    Navbar.full(
      brandUrl = "/",
      brandName = Some("PetClinic"),
      right = Seq(
        a(href := "/owners", cls := "nav-link")("Owners") -> Seq.empty,
        a(href := "/vets", cls := "nav-link")("Veterinarians") -> Seq.empty
      )
    ),
    div(bootstrapContainer)(
      pageContent
    )
  )

}
