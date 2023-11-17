package ba.sake.sharaf.petclinic.db.models

import java.time.LocalDate
import ba.sake.squery.SqlReadRow

case class VisitRow(
    id: Int,
    visit_date: Option[LocalDate],
    description: Option[String]
) derives SqlReadRow
