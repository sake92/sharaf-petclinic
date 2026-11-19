package ba.sake.sharaf.petclinic.domain.models

import io.scalaland.chimney.dsl.*
import ba.sake.sharaf.petclinic.db.models.owner.OwnerRow

case class Owner(
    id: Int,
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String,
    pets: Seq[Pet]
):
  def fullName = s"${firstName} ${lastName}"

  def toRow: OwnerRow = this
    .into[OwnerRow]
    .withFieldRenamed(_.firstName, _.first_name)
    .withFieldRenamed(_.lastName, _.last_name)
    .transform

object Owner:
  def fromRow(row: OwnerRow, pets: Seq[Pet]): Owner = row
    .into[Owner]
    .withFieldRenamed(_.first_name, _.firstName)
    .withFieldRenamed(_.last_name, _.lastName)
    .withFieldConst(_.pets, pets)
    .transform
