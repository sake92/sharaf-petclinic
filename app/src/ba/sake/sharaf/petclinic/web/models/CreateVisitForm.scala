package ba.sake.sharaf.petclinic.web.models

import java.time.LocalDate
import io.scalaland.chimney.dsl.*
import ba.sake.formson.FormDataRW
import ba.sake.validson.Validator
import ba.sake.sharaf.petclinic.domain.models.Visit

case class CreateVisitForm(
    date: LocalDate,
    description: String
) derives FormDataRW:
  def toNewVisit: Visit = this.transformInto[Visit]

object CreateVisitForm:

  val empty = CreateVisitForm(LocalDate.now(), "")

  given Validator[CreateVisitForm] = Validator
    .derived[CreateVisitForm]
    .and(_.date, _.isBefore(LocalDate.now().plusDays(1)), "must be today or in the past")
    .notBlank(_.description)
