package ba.sake.sharaf.petclinic.web.models

import ba.sake.formson.FormDataRW
import ba.sake.validson.Validator
import ba.sake.sharaf.petclinic.domain.models.Owner

// create/edit owner form data
case class UpsertOwnerForm(
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String
) derives FormDataRW

object UpsertOwnerForm:

  val empty = UpsertOwnerForm("", "", "", "", "")

  def fromOwner(o: Owner): UpsertOwnerForm =
    UpsertOwnerForm(o.firstName, o.lastName, o.address, o.city, o.telephone)

  given Validator[UpsertOwnerForm] = Validator
    .derived[UpsertOwnerForm]
    .and(_.firstName, !_.isBlank(), "must not be blank")
    .and(_.lastName, !_.isBlank(), "must not be blank")
    .and(_.address, !_.isBlank(), "must not be blank")
    .and(_.city, !_.isBlank(), "must not be blank")
    .and(_.telephone, t => t.trim.size == 10 && t.forall(_.isDigit), "must be 10-digit number")
