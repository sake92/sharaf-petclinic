package ba.sake.sharaf.petclinic.domain.models

import java.time.LocalDate
import ba.sake.sharaf.petclinic.common.PetType
import ba.sake.sharaf.petclinic.db.models.pet.PetRow

case class Pet(
    id: Int,
    name: String,
    birthDate: LocalDate,
    petType: PetType,
    visits: Seq[Visit]
) {
  def toRow: PetRow =
    PetRow(id, name, birthDate, petType)
}

object Pet {

  def fromRow(row: PetRow, visits: Seq[Visit]) =
    Pet(row.id, row.name, row.birth_date, row.pet_type, visits)
}
