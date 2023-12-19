package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.validson.*
import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.views.*
import ba.sake.sharaf.petclinic.web.models.CreateVisitForm

class VisitController(petService: PetService) extends PetclinicController {

  override def routes = Routes:

    case GET() -> Path("owners", param[Int](ownerId), "pets", param[Int](petId), "visits", "new") =>
      val pet = petService.findById(ownerId, petId).getOrElse(throw NotFoundException(s"Pet: $ownerId"))
      val formData = CreateVisitForm.empty
      val htmlPage = CreateVisitPage(ownerId, pet.id, formData, Seq.empty)
      Response.withBody(htmlPage)

    case POST() -> Path("owners", param[Int](ownerId), "pets", param[Int](petId), "visits", "new") =>
      val petOpt = petService.findById(ownerId, petId)
      petOpt match
        case Some(pet) =>
          val formData = Request.current.bodyForm[CreateVisitForm]
          formData.validate match
            case Seq() =>
              petService.insertVisit(petId, formData.toNewVisit)
              Response.redirect(s"/owners/${ownerId}")
            case errors =>
              val htmlPage = CreateVisitPage(ownerId, pet.id, formData, errors)
              Response.withBody(htmlPage).withStatus(400)
        case None =>
          Response.withStatus(404)
}
