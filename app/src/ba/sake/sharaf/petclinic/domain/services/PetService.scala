package ba.sake.sharaf.petclinic.domain.services

import ba.sake.sharaf.NotFoundException
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.pet.*
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.domain.models.*

class PetService(petDao: PetDao):

  def insert(ownerId: Int, petTypeId: Int, pet: Pet) =
    petDao.insert(ownerId, petTypeId, pet.toRow)

  def update(petTypeId: Int, pet: Pet): Unit =
    petDao.update(petTypeId, pet.toRow)

  def findById(ownerId: Int, id: Int): Option[Pet] =
    petDao.findById(ownerId, id).map(petRow => Pet.fromRow(petRow, Seq.empty))

  def getPetType(petType: PetType): PetTypeRow =
    petDao
      .findPetTypes()
      .find(_.name == petType)
      .getOrElse(throw NotFoundException(s"Pet type: ${petType}"))

  def insertVisit(petId: Int, visit: Visit) =
    petDao.insertVisit(petId, visit.toRow)
