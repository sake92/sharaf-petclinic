package ba.sake.sharaf.petclinic.db.models.owner

import ba.sake.squery.SqlReadRow
import ba.sake.sharaf.petclinic.db.models.pet.PetRow

case class OwnerWithPetRow(
    o: OwnerRow,
    p: Option[PetRow]
) derives SqlReadRow
