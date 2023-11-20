package ba.sake.sharaf.petclinic.web.views

import ba.sake.querson.toQueryString
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.models.*
import Bundle.*, Tags.*

class VetsPage(vetsPageRes: PageResponse[Vet]) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Veterinarians")

  override def pageContent: Frag = div(
    h1("Veterinarians"),
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Name"), th("Specialties")),
      vetsPageRes.items.map { vet =>
        tr(
          td(vet.fullName),
          td(vet.specialties.mkString(", "))
        )
      }
    ),
    fragments.pager(vetsPageRes, getLink)
  )

  private def getLink(pr: PageRequest): String =
    s"/vets?${pr.toQueryString()}"

}
