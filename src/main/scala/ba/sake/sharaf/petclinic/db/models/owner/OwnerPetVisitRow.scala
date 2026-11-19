package ba.sake.sharaf.petclinic.db.models
package owner

import ba.sake.squery.read.SqlReadRow
import ba.sake.sharaf.petclinic.db.models.pet.*

case class OwnerPetVisitRow(
    o: OwnerRow,
    p: Option[PetRow],
    v: Option[VisitRow]
) derives SqlReadRow
