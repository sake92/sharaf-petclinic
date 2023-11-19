package ba.sake.sharaf.petclinic.web.views

import Bundle.*, Tags.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.models.*

class VetsPage(vetsPageRes: PageResponse[Vet]) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Veterinarians")

  override def pageContent: Frag = div(
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Name"), th("Specialties")),
      vetsPageRes.items.map { vet =>
        tr(
          td(vet.fullName),
          td(vet.specialties.mkString(", "))
        )
      }
    )
  )

}
