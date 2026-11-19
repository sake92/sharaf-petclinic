package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.sharaf.*
import ba.sake.sharaf.hepek.given
import ba.sake.sharaf.petclinic.common.PageRequest
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.views.*

class VetController(vetService: VetService) extends SharafController:

  override def routes = Routes:
    case GET -> Path("vets") =>
      val pageReq = Request.current.queryParamsValidated[PageRequest]
      val pageRes = vetService.findAll(pageReq)
      val htmlPage = VetsPage(pageRes)
      Response.withBody(htmlPage)
