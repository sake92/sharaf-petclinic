package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.web.views.ViewsFactory

class PetController(petDao: PetDao) extends PetclinicController {

  override def routes = Routes:
    case GET() -> Path("pets", param[Int](id)) =>
      val res = petDao.findById(id, 1)
      Response.withBody(ViewsFactory.welcome)

}
