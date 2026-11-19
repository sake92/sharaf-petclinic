package ba.sake.sharaf.petclinic.db.models
package pet

import ba.sake.squery.SqlReadRow
import ba.sake.sharaf.petclinic.common.PetType

case class PetTypeRow(
    id: Int,
    name: PetType
) derives SqlReadRow
