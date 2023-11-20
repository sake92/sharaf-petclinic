package ba.sake.sharaf.petclinic.web.views

import ba.sake.hepek.html.HtmlPage
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.models.*
import ba.sake.sharaf.petclinic.web.models.SearchOwnerQP

object ViewsFactory {

  def welcome: HtmlPage =
    WelcomePage()

  def vets(vetsPageRes: PageResponse[Vet]): HtmlPage =
    VetsPage(vetsPageRes)

  def findOwners(): HtmlPage =
    FindOwnersPage()
  
  def owners(qp: SearchOwnerQP, ownersPageRes: PageResponse[Owner]): HtmlPage =
    OwnersPage(qp, ownersPageRes)
  
}
