package ba.sake.sharaf.petclinic.domain.services

import ba.sake.squery.SqueryContext
import ba.sake.sharaf.exceptions.NotFoundException
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.pet.*
import ba.sake.sharaf.petclinic.db.daos.*
import ba.sake.sharaf.petclinic.domain.models.*

class PetService(ctx: SqueryContext, petDao: PetDao):

  def insert(ownerId: Int, petTypeId: Int, pet: Pet) = ctx.run {
    petDao.insert(ownerId, petTypeId, pet.toRow)
  }

  def update(petTypeId: Int, pet: Pet): Unit = ctx.run {
    petDao.update(petTypeId, pet.toRow)
  }

  def findById(ownerId: Int, id: Int): Option[Pet] = ctx.run {
    petDao.findById(ownerId, id).map(petRow => Pet.fromRow(petRow, Seq.empty))
  }

  def getPetType(petType: PetType): PetTypeRow = ctx.run {
    petDao
      .findPetTypes()
      .find(_.name == petType)
      .getOrElse(throw NotFoundException(s"Pet type: ${petType}"))
  }

  def insertVisit(petId: Int, visit: Visit) = ctx.run {
    petDao.insertVisit(petId, visit.toRow)
  }
