package ba.sake.sharaf.petclinic.db.models.owner

import ba.sake.squery.SqlReadRow

case class OwnerRow(
    id: Int,
    first_name: Option[String],
    last_name: Option[String],
    address: Option[String],
    city: Option[String],
    telephone: Option[String]
) derives SqlReadRow
