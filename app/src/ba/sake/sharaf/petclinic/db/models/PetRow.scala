package ba.sake.sharaf.petclinic.db.models

import java.time.LocalDate
import ba.sake.squery.SqlReadRow

case class PetRow(
    id: Int,
    name: Option[String],
    birth_date: Option[LocalDate]
) derives SqlReadRow
