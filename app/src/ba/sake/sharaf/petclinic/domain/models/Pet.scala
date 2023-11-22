package ba.sake.sharaf.petclinic.domain.models

import java.time.LocalDate
import ba.sake.sharaf.petclinic.db.models.pet.PetRow

case class Pet(
    id: Int,
    name: String,
    birthDate: LocalDate,
    petType: String,
    visits: Seq[Visit]
)

object Pet {

  def fromRow(row: PetRow, petType: String, visits: Seq[Visit]) =
    Pet(row.id, row.name, row.birth_date, petType, visits)
}
