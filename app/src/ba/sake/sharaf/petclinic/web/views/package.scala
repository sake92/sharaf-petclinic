package ba.sake.sharaf.petclinic.web.views

import ba.sake.hepek.bootstrap5.BootstrapBundle
import ba.sake.sharaf.petclinic.common.*

val Bundle: BootstrapBundle = BootstrapBundle()

object helpers {
  // scala complains if these are top-level
  import Bundle.*
  import Bundle.Tags.*

  val bsTablePrimary = table(Classes.tableClass, cls := "table-info", Classes.tableHoverable, Classes.tableStriped)
  val bsTableSecondary =
    table(Classes.tableClass, cls := "table-secondary table-sm", Classes.tableHoverable, Classes.tableStriped)
}

object fragments {
  import Bundle.Tags.*

  def pagination[T](pageRes: PageResponse[T], getLink: PageRequest => String) =
    tag("nav")(
      ul(cls := "pagination")(
        li(
          cls := "page-item",
          Option.when(pageRes.isFirst)(cls := "disabled")
        )(
          a(href := getLink(pageRes.prevPageReq), cls := "page-link")("Previous")
        ),
        Seq.range(0, pageRes.totalPages).map { idx =>
          li(
            cls := "page-item",
            Option.when(pageRes.number == idx)(cls := "active")
          )(
            a(href := getLink(PageRequest(idx)), cls := "page-link")(s"${idx + 1}")
          )
        },
        li(
          cls := "page-item",
          Option.when(pageRes.isLast)(cls := "disabled")
        )(
          a(href := getLink(pageRes.nextPageReq), cls := "page-link")("Next")
        )
      )
    )

}
