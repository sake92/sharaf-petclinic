package ba.sake.sharaf.petclinic.db.models.vet

import ba.sake.squery.SqlReadRow

case class VetRow(
    id: Int,
    first_name: Option[String],
    last_name: Option[String]
) derives SqlReadRow
