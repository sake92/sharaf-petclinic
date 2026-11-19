package ba.sake.sharaf.petclinic.db.models.pet

import java.time.LocalDate
import ba.sake.squery.SqlReadRow

case class VisitRow(
    id: Int,
    visit_date: LocalDate,
    description: String
) derives SqlReadRow
