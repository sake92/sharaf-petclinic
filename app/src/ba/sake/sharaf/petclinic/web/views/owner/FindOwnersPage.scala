package ba.sake.sharaf.petclinic.web.views
package owner

import Bundle.*, Tags.*

class FindOwnersPage() extends PetclinicPage {

  override def pageSettings = super.pageSettings
    .withTitle("Find Owners")

  override def pageContent: Frag = div(
    h1("Find Owners"),
    Form.form(action := "/owners", method := "GET")(
      Form.inputText(tpe := "search", autofocus)("q", "Last Name"),
      Form.inputButton(tpe := "submit", Classes.btnPrimary)("Find Owner")
    ),
    a(href := "/owners/new", Classes.btnClass, Classes.btnLink)("Add Owner")
  )

}
