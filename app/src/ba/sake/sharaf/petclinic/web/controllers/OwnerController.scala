package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.validson.*
import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.domain.models.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.views.ViewsFactory
import ba.sake.sharaf.petclinic.web.models.*

class OwnerController(ownerService: OwnerService) extends PetclinicController {

  override def routes = Routes:

    case GET() -> Path("owners", "find") =>
      Response.withBody(ViewsFactory.findOwners())

    case GET() -> Path("owners") =>
      val qp = Request.current.queryParamsValidated[FindOwnerQP]
      val pageRes = ownerService.findByLastName(qp.p, qp.q.getOrElse(""))
      if pageRes.items.size == 1 then
        val owner = pageRes.items.head
        Response.redirect(s"/owners/${owner.id}")
      else Response.withBody(ViewsFactory.owners(qp, pageRes))

    case GET() -> Path("owners", param[Int](ownerId)) =>
      val ownerOpt = ownerService.findById(ownerId)
      val htmlPageOpt = ownerOpt.map(ViewsFactory.ownerDetails)
      Response.withBodyOpt(htmlPageOpt, "Owner")

    case GET() -> Path("owners", "new") =>
      Response.withBody(ViewsFactory.newOwner(UpsertOwnerForm.empty, Seq.empty))

    case POST() -> Path("owners", "new") =>
      val formData = Request.current.bodyForm[UpsertOwnerForm]
      formData.validate match
        case Seq() =>
          val newOwner = ownerService.insert(formData.toNewOwner)
          Response.redirect(s"/owners/${newOwner.id}")
        case errors =>
          val htmlPage = ViewsFactory.newOwner(formData, errors)
          Response.withBody(htmlPage).withStatus(400)

    case GET() -> Path("owners", param[Int](ownerId), "edit") =>
      val ownerOpt = ownerService.findById(ownerId)
      val htmlPageOpt = ownerOpt.map { owner =>
        val formData = UpsertOwnerForm.fromOwner(owner)
        ViewsFactory.editOwner(owner.id, formData, Seq.empty)
      }
      Response.withBodyOpt(htmlPageOpt, "Owner")

    case POST() -> Path("owners", param[Int](ownerId), "edit") =>
      val ownerOpt = ownerService.findById(ownerId)
      ownerOpt match
        case Some(owner) =>
          val formData = Request.current.bodyForm[UpsertOwnerForm]
          formData.validate match
            case Seq() =>
              val updatedOwner = owner.copy(
                firstName = formData.firstName,
                lastName = formData.lastName,
                address = formData.address,
                city = formData.city,
                telephone = formData.telephone
              )
              ownerService.update(updatedOwner)
              Response.redirect(s"/owners/${ownerId}")
            case errors =>
              val htmlPage = ViewsFactory.editOwner(ownerId, formData, errors)
              Response.withBody(htmlPage).withStatus(400)
        case None =>
          Response.withStatus(404)

}
