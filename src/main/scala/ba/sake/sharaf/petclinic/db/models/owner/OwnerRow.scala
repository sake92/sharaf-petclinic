package ba.sake.sharaf.petclinic.db.models.owner

import ba.sake.squery.SqlReadRow

case class OwnerRow(
    id: Int,
    first_name: String,
    last_name: String,
    address: String,
    city: String,
    telephone: String
) derives SqlReadRow
