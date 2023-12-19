package ba.sake.sharaf.petclinic.domain.models

import java.time.LocalDate
import io.scalaland.chimney.dsl.*
import ba.sake.sharaf.petclinic.common.PetType
import ba.sake.sharaf.petclinic.db.models.pet.PetRow

case class Pet(
    id: Int,
    name: String,
    birthDate: LocalDate,
    petType: PetType,
    visits: Seq[Visit]
):
  def toRow: PetRow = this
    .into[PetRow]
    .withFieldRenamed(_.birthDate, _.birth_date)
    .withFieldRenamed(_.petType, _.pet_type)
    .transform

object Pet:
  def fromRow(row: PetRow, visits: Seq[Visit]): Pet = row
    .into[Pet]
    .withFieldRenamed(_.birth_date, _.birthDate)
    .withFieldRenamed(_.pet_type, _.petType)
    .withFieldConst(_.visits, visits)
    .transform
