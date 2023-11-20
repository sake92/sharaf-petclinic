package ba.sake.sharaf.petclinic.web.views

import ba.sake.sharaf.petclinic.common.*
import Bundle.Tags.*

object fragments {

  def pager[T](pageRes: PageResponse[T], getLink: PageRequest => String) = 
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
