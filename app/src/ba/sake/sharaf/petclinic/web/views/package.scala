package ba.sake.sharaf.petclinic.web.views

import ba.sake.hepek.bootstrap5.BootstrapBundle

val Bundle: BootstrapBundle = BootstrapBundle()

// scala complains if these are top-level
object helpers {

  import Bundle.*
  import Tags.*

  val bsTablePrimary = table(Classes.tableClass, Classes.tableHoverable, Classes.tableStriped, cls := "table-primary")
  val bsTableSecondary =
    table(Classes.tableClass, Classes.tableHoverable, Classes.tableStriped, cls := "table-secondary table-sm")
}
