package ba.sake.sharaf.petclinic.db.models

import ba.sake.squery.SqlReadRow

case class VetWithSpecialtyRow(
    v: VetRow,
    s: Option[SpecialtyRow]
) derives SqlReadRow
