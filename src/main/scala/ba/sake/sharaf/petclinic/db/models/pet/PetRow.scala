package ba.sake.sharaf.petclinic.db.models
package pet

import java.time.LocalDate
import ba.sake.squery.SqlReadRow
import ba.sake.sharaf.petclinic.common.PetType

case class PetRow(
    id: Int,
    name: String,
    birth_date: LocalDate,
    pet_type: PetType
) derives SqlReadRow
