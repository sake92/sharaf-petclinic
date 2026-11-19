package ba.sake.sharaf.petclinic.db.models.vet

import ba.sake.squery.SqlReadRow

case class SpecialtyRow(
    id: Int,
    name: Option[String]
) derives SqlReadRow
