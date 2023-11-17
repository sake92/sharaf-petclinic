package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.common.PageRequest
import ba.sake.sharaf.petclinic.db.daos.VetDao
import ba.sake.sharaf.petclinic.web.views.ViewsFactory
import ba.sake.sharaf.petclinic.web.models.given

class VetController(vetDao: VetDao) extends PetclinicController {

  override def routes = Routes:
    case GET() -> Path("vets") =>
      val pageReq = Request.current.queryParamsValidated[PageRequest]
      val res = vetDao.findAll(pageReq)
      println(res)
      Response.withBody(ViewsFactory.welcome)

}
