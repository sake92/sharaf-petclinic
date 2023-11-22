package ba.sake.sharaf.petclinic.web.views
package owner

import ba.sake.validson.ValidationError
import ba.sake.sharaf.petclinic.web.models.UpsertOwnerForm
import Bundle.*, Tags.*

class CreateOrEditOwnerPage(
    ownerId: Option[Int],
    formData: UpsertOwnerForm,
    errors: Seq[ValidationError]
) extends PetclinicPage {

  private val (pageTitle, actionLabel, formAction) = ownerId match
    case Some(ownerId) => ("Edit Owner", "Update", s"/owners/${ownerId}/edit")
    case None          => ("Create Owner", "Create", "/owners/new")

  override def pageSettings = super.pageSettings
    .withTitle(pageTitle)

  override def pageContent: Frag = div(
    h1("Find Owners"),
    Form.form(action := formAction, method := "POST")(
      withValueAndValidation("firstName", _.firstName) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(autofocus, value := fieldValue)(
          fieldName,
          "First Name",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("lastName", _.lastName) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(value := fieldValue)(
          fieldName,
          "Last Name",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("address", _.address) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(value := fieldValue)(
          fieldName,
          "Address",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("city", _.city) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(value := fieldValue)(
          fieldName,
          "City",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("telephone", _.telephone) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(value := fieldValue)(
          fieldName,
          "Telephone",
          _validationState = state,
          _messages = messages
        )
      },
      br,
      Form.inputButton(tpe := "submit", Classes.btnPrimary)(actionLabel)
    )
  )

  // errors are returned as JSON Path, hence the $. prefix below!
  private def withValueAndValidation(fieldName: String, extract: UpsertOwnerForm => String)(
      f: (String, String, Option[Form.ValidationState], Seq[String]) => Frag
  ) =
    val fieldErrors = errors.filter(_.path == s"$$.$fieldName")
    val (state, errMsgs) =
      if fieldErrors.isEmpty then None -> Seq.empty
      else Some(Form.ValidationState.Error) -> fieldErrors.map(_.msg)
    f(fieldName, extract(formData), state, errMsgs)

}
