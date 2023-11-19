package ba.sake.sharaf.petclinic.web.views

import ba.sake.hepek.html.HtmlPage
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.models.Vet

object ViewsFactory {

  def welcome: HtmlPage = WelcomePage()

  def vets(vetsPage: PageResponse[Vet]): HtmlPage = VetsPage(vetsPage)
}
