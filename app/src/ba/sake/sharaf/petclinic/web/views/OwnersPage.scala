package ba.sake.sharaf.petclinic.web.views

import ba.sake.querson.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class OwnersPage(qp: SearchOwnerQP, ownersPageRes: PageResponse[Owner]) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Owners")

  override def pageContent: Frag = div(
    h1("Owners"),
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Name"), th("Address"), th("City"), th("Telephone"), th("Pets")),
      ownersPageRes.items.map { owner =>
        tr(
          td(owner.fullName),
          td(owner.address),
          td(owner.city),
          td(owner.telephone),
          td(owner.pets.mkString(", "))
        )
      }
    ),
    fragments.pagination(ownersPageRes, getLink)
  )

  private def getLink(pr: PageRequest): String =
    val newQP = qp.copy(p = pr)
    s"/owners?${newQP.toQueryString()}"

}
