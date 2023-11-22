package ba.sake.sharaf.petclinic.web.views

import ba.sake.validson.ValidationError
import ba.sake.hepek.html.HtmlPage
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.web.models.*
import ba.sake.sharaf.petclinic.web.views.owner.*

object ViewsFactory {

  def welcome: HtmlPage =
    WelcomePage()

  def vets(vetsPageRes: PageResponse[Vet]): HtmlPage =
    VetsPage(vetsPageRes)

  def findOwners(): HtmlPage =
    FindOwnersPage()

  def ownerDetails(owner: Owner): HtmlPage =
    OwnerDetailsPage(owner)

  def owners(qp: FindOwnerQP, ownersPageRes: PageResponse[Owner]): HtmlPage =
    OwnersPage(qp, ownersPageRes)

  def newOwner(formData: UpsertOwnerForm, errors: Seq[ValidationError]): HtmlPage =
    CreateOrEditOwnerPage(None, formData, errors)

  def editOwner(ownerId: Int, formData: UpsertOwnerForm, errors: Seq[ValidationError]): HtmlPage =
    CreateOrEditOwnerPage(Some(ownerId), formData, errors)

  def newPet(ownerId: Int, formData: UpsertPetForm, errors: Seq[ValidationError]): HtmlPage =
    CreateOrEditPetPage(ownerId, None, formData, errors)

  def editPet(ownerId: Int, petId: Int, formData: UpsertPetForm, errors: Seq[ValidationError]): HtmlPage =
    CreateOrEditPetPage(ownerId, Some(petId), formData, errors)

}
