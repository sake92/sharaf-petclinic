package ba.sake.sharaf.petclinic.db.models.owner

import ba.sake.squery.read.SqlReadRow
import ba.sake.sharaf.petclinic.db.models.pet.*

case class OwnerPetVisitRow(
    o: OwnerRow,
    p: Option[PetRow],
    petType: Option[String],
    v: Option[VisitRow]
) derives SqlReadRow
