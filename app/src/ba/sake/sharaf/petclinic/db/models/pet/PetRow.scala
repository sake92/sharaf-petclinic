package ba.sake.sharaf.petclinic.db.models.pet

import java.time.LocalDate
import ba.sake.squery.SqlReadRow

case class PetRow(
    id: Int,
    name: Option[String],
    birth_date: Option[LocalDate]
) derives SqlReadRow
