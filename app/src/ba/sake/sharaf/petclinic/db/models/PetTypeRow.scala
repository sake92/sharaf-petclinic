package ba.sake.sharaf.petclinic.db.models

import ba.sake.squery.SqlReadRow

case class PetTypeRow(
    id: Int,
    name: Option[String]
) derives SqlReadRow
