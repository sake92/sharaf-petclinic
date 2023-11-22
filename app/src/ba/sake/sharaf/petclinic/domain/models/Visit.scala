package ba.sake.sharaf.petclinic.domain.models

import java.time.LocalDate
import ba.sake.sharaf.petclinic.db.models.pet.VisitRow

case class Visit(
    date: LocalDate,
    description: String
) {
  def toRow: VisitRow =
    VisitRow(-1, date, description)
}

object Visit {
  def fromRow(row: VisitRow) =
    Visit(row.visit_date, row.description)
}
