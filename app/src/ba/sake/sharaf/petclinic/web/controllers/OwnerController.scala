package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.services.*
import ba.sake.sharaf.petclinic.web.views.ViewsFactory
import ba.sake.sharaf.petclinic.web.models.SearchOwnerQP

class OwnerController(ownerService: OwnerService) extends PetclinicController {

  override def routes = Routes:

    case GET() -> Path("owners", "find") =>
      Response.withBody(ViewsFactory.findOwners())

    case GET() -> Path("owners") =>
      val qp = Request.current.queryParamsValidated[SearchOwnerQP]
      val pageRes = ownerService.findByLastName(qp.p, qp.q.getOrElse(""))
      Response.withBody(ViewsFactory.owners(qp, pageRes))

}
