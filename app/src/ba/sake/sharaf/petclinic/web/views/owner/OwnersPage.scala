package ba.sake.sharaf.petclinic.web.views
package owner

import ba.sake.querson.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class OwnersPage(qp: FindOwnerQP, ownersPageRes: PageResponse[Owner]) extends PetclinicPage:

  override def pageSettings = super.pageSettings
    .withTitle("Owners")

  override def pageContent: Frag = div(
    h1("Owners"),
    helpers.bsTablePrimary(
      tr(th("Name"), th("Address"), th("City"), th("Telephone"), th("Pets")),
      ownersPageRes.items.map { owner =>
        tr(
          td(a(href := s"/owners/${owner.id}")(owner.fullName)),
          td(owner.address),
          td(owner.city),
          td(owner.telephone),
          td(owner.pets.map(_.name).mkString(", "))
        )
      }
    ),
    fragments.pagination(ownersPageRes, getLink)
  )

  private def getLink(pr: PageRequest): String =
    val newQP = qp.copy(p = pr)
    s"/owners?${newQP.toQueryString()}"
