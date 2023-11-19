package ba.sake.sharaf.petclinic.web.views

import ba.sake.querson.*
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
    ),
    tag("nav")(
      ul(cls := "pagination")(
        li(
          cls := "page-item",
          Option.when(vetsPageRes.isFirst)(cls := "disabled")
        )(
          a(href := "/vets?" + vetsPageRes.prevPageReq.toQueryString(), cls := "page-link")("Previous")
        ),
        Seq.range(0, vetsPageRes.totalPages).map { idx =>
          li(
            cls := "page-item",
            Option.when(vetsPageRes.number == idx)(cls := "active")
          )(
            a(href := "/vets?" + PageRequest(idx).toQueryString(), cls := "page-link")(s"${idx + 1}")
          )
        },
        li(
          cls := "page-item",
          Option.when(vetsPageRes.isLast)(cls := "disabled")
        )(
          a(href := "/vets?" + vetsPageRes.nextPageReq.toQueryString(), cls := "page-link")("Next")
        )
      )
    )
  )

}
