package ba.sake.sharaf.petclinic.domain.models

import ba.sake.sharaf.petclinic.db.models.owner.OwnerRow

case class Owner(
    id: Int,
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String,
    pets: Seq[Pet] = Seq.empty
) {
  def fullName = s"${firstName} ${lastName}"

  def toRow: OwnerRow =
    OwnerRow(id, firstName, lastName, address, city, telephone)
}

object Owner {

  def fromRow(row: OwnerRow, pets: Seq[Pet]): Owner =
    Owner(row.id, row.first_name, row.last_name, row.address, row.city, row.telephone, pets)
}
