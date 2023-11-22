package ba.sake.sharaf.petclinic.web.controllers

import ba.sake.validson.*
import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.petclinic.domain.services.*
import ba.sake.sharaf.petclinic.web.views.ViewsFactory
import ba.sake.sharaf.petclinic.web.models.UpsertPetForm

class PetController(ownerService: OwnerService, petService: PetService) extends PetclinicController {

  override def routes = Routes:

    case GET() -> Path("owners", param[Int](ownerId), "pets", "new") =>
      Response.withBody(ViewsFactory.newPet(ownerId, UpsertPetForm.empty, Seq.empty))

    case POST() -> Path("owners", param[Int](ownerId), "pets", "new") =>
      val formData = Request.current.bodyForm[UpsertPetForm]
      formData.validate match
        case Seq() =>
          // make sure owner exists
          ownerService.findById(ownerId).getOrElse(throw NotFoundException(s"Owner: $ownerId"))
          val petTypeId = petService.getPetType(formData.petType).id
          petService.insert(ownerId, petTypeId, formData.toNewPet)
          Response.redirect(s"/owners/${ownerId}")
        case errors =>
          val htmlPage = ViewsFactory.newPet(ownerId, formData, errors)
          Response.withBody(htmlPage).withStatus(400)

    case GET() -> Path("owners", param[Int](ownerId), "pets", param[Int](petId), "edit") =>
      val petOpt = petService.findById(ownerId, petId)
      val htmlPageOpt = petOpt.map { pet =>
        val formData = UpsertPetForm.fromPet(pet)
        ViewsFactory.editPet(ownerId, pet.id, formData, Seq.empty)
      }
      Response.withBodyOpt(htmlPageOpt, "Pet")

    case POST() -> Path("owners", param[Int](ownerId), "pets", param[Int](petId), "edit") =>
      val petOpt = petService.findById(ownerId, petId)
      petOpt match
        case Some(pet) =>
          val formData = Request.current.bodyForm[UpsertPetForm]
          formData.validate match
            case Seq() =>
              val updatedPet = pet.copy(
                name = formData.name,
                birthDate = formData.birthDate,
                petType = formData.petType
              )
              val petTypeId = petService.getPetType(formData.petType).id
              petService.update(petTypeId, updatedPet)
              Response.redirect(s"/owners/${ownerId}")
            case errors =>
              val htmlPage = ViewsFactory.editPet(ownerId, pet.id, formData, errors)
              Response.withBody(htmlPage).withStatus(400)
        case None =>
          Response.withStatus(404)
}
