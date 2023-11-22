package ba.sake.sharaf.petclinic.web.models

import java.time.LocalDate
import ba.sake.formson.FormDataRW
import ba.sake.validson.Validator
import ba.sake.sharaf.petclinic.domain.models.Visit

// create visit form data
case class CreateVisitForm(
    date: LocalDate,
    description: String
) derives FormDataRW:

  def toNewVisit: Visit =
    Visit(date, description)

object CreateVisitForm:

  val empty = CreateVisitForm(LocalDate.now(), "")

  given Validator[CreateVisitForm] = Validator
    .derived[CreateVisitForm]
    .and(_.date, _.isBefore(LocalDate.now().plusDays(1)), "must be today or in the past")
    .and(_.description, !_.isBlank(), "must not be blank")
