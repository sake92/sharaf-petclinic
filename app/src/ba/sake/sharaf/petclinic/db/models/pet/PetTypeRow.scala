package ba.sake.sharaf.petclinic.db.models.pet

import ba.sake.squery.SqlReadRow

case class PetTypeRow(
    id: Int,
    name: String
) derives SqlReadRow
