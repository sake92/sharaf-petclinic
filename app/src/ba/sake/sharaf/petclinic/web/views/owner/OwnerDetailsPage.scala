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
      tr(th("Name"), th("Birth Date"), th("Type"), th("Visits")),
      owner.pets.map { pet =>
        tr(
          td(pet.name),
          td(pet.birthDate.toString()),
          td(pet.petType),
          td(
            helpers.bsTableSecondary(
              tr(th("Date"), th("Description")),
              pet.visits.map { visit =>
                tr(
                  td(visit.date.toString()),
                  td(visit.description)
                )
              }
            )
          )
        )
      }
    )
  )

}
