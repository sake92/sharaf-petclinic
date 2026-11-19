package ba.sake.sharaf.petclinic.db.models.vet

import ba.sake.squery.SqlReadRow

case class VetSpecialtyRow(
    v: VetRow,
    s: Option[SpecialtyRow]
) derives SqlReadRow
