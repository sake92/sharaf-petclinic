package ba.sake.sharaf.petclinic.web.views

import ba.sake.querson.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.models.*
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class OwnersPage(qp: SearchOwnerQP, ownersPageRes: PageResponse[Owner]) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Owners")

  override def pageContent: Frag = div(
    table(Classes.tableClass, Classes.tableHoverable)(
      tr(th("Name"), th("Specialties")),
      ownersPageRes.items.map { owner =>
        tr(
          td(owner.fullName),
          td(owner.id)
        )
      }
    ),
    fragments.pager(ownersPageRes, getLink)
  )

  private def getLink(pr: PageRequest): String =
    val newQP = qp.copy(p = pr)
    s"/owners?${newQP.toQueryString()}"

}
