package ba.sake.sharaf.petclinic.web.views

import ba.sake.validson.ValidationError
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class CreateOrEditPetPage(
    ownerId: Int,
    petId: Option[Int],
    formData: UpsertPetForm,
    errors: Seq[ValidationError]
) extends PetclinicPage {

  private val (pageTitle, actionLabel, formAction) = petId match
    case Some(id) => ("Edit Pet", "Update", s"/owners/${ownerId}/pets/${id}/edit")
    case None     => ("Create Pet", "Create", s"/owners/${ownerId}/pets/new")

  override def pageSettings = super.pageSettings
    .withTitle(pageTitle)

  override def pageContent: Frag = div(
    h1(pageTitle),
    Form.form(action := formAction, method := "POST")(
      withValueAndValidation("name", _.name) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(autofocus, value := fieldValue)(
          fieldName,
          "Name",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("birthDate", _.birthDate.toString) { case (fieldName, fieldValue, state, messages) =>
        Form.inputDate(value := fieldValue)(
          fieldName,
          "Birth Date",
          _validationState = state,
          _messages = messages
        )
      },
      Form.inputSelect()(
        "petType",
        PetType.sortedValues.map { petType =>
          (petType, petType, Option.when(formData.petType.toString == petType)(selected).toSeq)
        },
        "Type"
      ),
      br,
      Form.inputButton(tpe := "submit", Classes.btnPrimary)(actionLabel)
    )
  )

  // errors are returned as JSON Path, hence the $. prefix below!
  private def withValueAndValidation(fieldName: String, extract: UpsertPetForm => String)(
      f: (String, String, Option[Form.ValidationState], Seq[String]) => Frag
  ) =
    val fieldErrors = errors.filter(_.path == s"$$.$fieldName")
    val (state, errMsgs) =
      if fieldErrors.isEmpty then None -> Seq.empty
      else Some(Form.ValidationState.Error) -> fieldErrors.map(_.msg)
    f(fieldName, extract(formData), state, errMsgs)

}
