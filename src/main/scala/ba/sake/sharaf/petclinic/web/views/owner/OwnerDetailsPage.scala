package ba.sake.sharaf.petclinic.web.views
package owner

import ba.sake.sharaf.petclinic.domain.models.*
import Bundle.*, Tags.*

class OwnerDetailsPage(owner: Owner) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Owner details")

  override def pageContent: Frag = div(
    h1("Owner details"),
    hr,
    h2("Basic info"),
    helpers.bsTablePrimary(
      tr(th("Name"), th("Address"), th("City"), th("Telephone")),
      tr(
        td(owner.fullName),
        td(owner.address),
        td(owner.city),
        td(owner.telephone)
      )
    ),
    a(href := s"/owners/${owner.id}/edit")("Edit Owner"),
    hr,
    h2("Pets"),
    helpers.bsTablePrimary(
      tr(th( /*actions*/ ), th("Name"), th("Birth Date"), th("Type"), th("Visits")),
      owner.pets.map { pet =>
        tr(
          td(a(href := s"/owners/${owner.id}/pets/${pet.id}/edit")("Edit")),
          td(pet.name),
          td(pet.birthDate.toString),
          td(pet.petType.toString),
          td(
            if pet.visits.isEmpty then div("No visits yet", br)
            else
              helpers.bsTableSecondary(
                tr(th("Date"), th("Description")),
                pet.visits.map { visit =>
                  tr(
                    td(visit.date.toString()),
                    td(visit.description)
                  )
                }
              )
            ,
            a(href := s"/owners/${owner.id}/pets/${pet.id}/visits/new")("Add visit")
          )
        )
      }
    ),
    a(href := s"/owners/${owner.id}/pets/new")("Add Pet")
  )

}
