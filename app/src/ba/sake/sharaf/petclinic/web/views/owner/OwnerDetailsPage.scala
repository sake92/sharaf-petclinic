package ba.sake.sharaf.petclinic.web.views
package owner

import ba.sake.querson.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class OwnerDetailsPage(owner: Owner) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Owner details")

  override def pageContent: Frag = div(
    h1("Owner details"),
    hr,
    h2("Basic info"),
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Name"), th("Address"), th("City"), th("Telephone")),
      tr(
        td(owner.fullName),
        td(owner.address),
        td(owner.city),
        td(owner.telephone)
      )
    ),
    hr,
    h2("Pets"),
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Pet"), th("Visits")),
      owner.pets.map { pet =>
        tr(
          td(pet.name),
          td(pet.visits.toString)
        )
      }
    )
  )

}
