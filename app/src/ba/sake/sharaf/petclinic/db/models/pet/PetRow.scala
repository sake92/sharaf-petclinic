package ba.sake.sharaf.petclinic.db.models.pet

import java.time.LocalDate
import ba.sake.squery.SqlReadRow

case class PetRow(
    id: Int,
    name: String,
    birth_date: LocalDate
) derives SqlReadRow
