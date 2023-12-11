package ba.sake.sharaf.petclinic.db.models.owner

import ba.sake.squery.SqlReadRow
import ba.sake.sharaf.petclinic.db.models.pet.PetRow

// TODO remove With
case class OwnerWithPetRow(
    o: OwnerRow,
    p: Option[PetRow]
) derives SqlReadRow
