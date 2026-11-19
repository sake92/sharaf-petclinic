package ba.sake.sharaf.petclinic.web.views

import ba.sake.validson.ValidationError
import ba.sake.sharaf.petclinic.web.models.*
import Bundle.*, Tags.*

class CreateVisitPage(
    ownerId: Int,
    petId: Int,
    formData: CreateVisitForm,
    errors: Seq[ValidationError]
) extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Create Visit")

  override def pageContent: Frag = div(
    h1("Create Visit"),
    Form.form(action := s"/owners/${ownerId}/pets/${petId}/visits/new", method := "POST")(
      withValueAndValidation("date", _.date.toString) { case (fieldName, fieldValue, state, messages) =>
        Form.inputDate(autofocus, value := fieldValue)(
          fieldName,
          "Date",
          _validationState = state,
          _messages = messages
        )
      },
      withValueAndValidation("description", _.description) { case (fieldName, fieldValue, state, messages) =>
        Form.inputText(value := fieldValue)(
          fieldName,
          "Description",
          _validationState = state,
          _messages = messages
        )
      },
      br,
      Form.inputButton(tpe := "submit", Classes.btnPrimary)("Create")
    )
  )

  // errors are returned as JSON Path, hence the $. prefix below!
  private def withValueAndValidation(fieldName: String, extract: CreateVisitForm => String)(
      f: (String, String, Option[Form.ValidationState], Seq[String]) => Frag
  ) =
    val fieldErrors = errors.filter(_.path == s"$$.$fieldName")
    val (state, errMsgs) =
      if fieldErrors.isEmpty then None -> Seq.empty
      else Some(Form.ValidationState.Error) -> fieldErrors.map(_.msg)
    f(fieldName, extract(formData), state, errMsgs)

}
