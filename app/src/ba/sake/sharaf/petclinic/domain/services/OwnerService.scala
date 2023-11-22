package ba.sake.sharaf.petclinic.domain.services

import scala.collection.mutable
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.owner.*
import ba.sake.sharaf.petclinic.db.models.pet.*
import ba.sake.sharaf.petclinic.db.daos.OwnerDao
import ba.sake.sharaf.petclinic.domain.models.*

class OwnerService(ownerDao: OwnerDao) {

  def insert(owner: Owner): Owner =
    // id is autogenerated..
    var ownerRow = OwnerRow(
      owner.id,
      owner.firstName,
      owner.address,
      owner.lastName,
      owner.city,
      owner.telephone
    )
    ownerRow = ownerDao.insert(ownerRow) // new id assigned :)
    owner.copy(id = ownerRow.id)

  def findById(id: Int): Option[Owner] = {
    val rawRows = ownerDao.findById(id)
    if rawRows.isEmpty then None
    else {
      val ownerRow = rawRows.head.o

      // group by pet.id, preserving sort order
      // aka "poor-man's ORM"
      val petsMap = mutable.LinkedHashMap.empty[Int, (PetRow, String, Seq[OwnerPetVisitRow])]
      rawRows.foreach { row =>
        (row.p.zip(row.petType)).foreach { case (petRow, petType) =>
          val petId = petRow.id
          val (_, _, petRows) = petsMap.getOrElse(petId, (petRow, petType, Seq.empty))
          petsMap(petId) = (petRow, petType, petRows.appended(row))
        }
      }

      val pets = petsMap.map { case (petId, (petRow, petType, rows)) =>
        val visits = rows.flatMap { row =>
          row.v.map { visitRow =>
            Visit(visitRow.visit_date, visitRow.description)
          }
        }
        Pet.fromRow(petRow, petType, visits)
      }

      Some(Owner.fromRow(ownerRow, pets.toSeq))
    }
  }

  def findByLastName(req: PageRequest, lastName: String): PageResponse[Owner] = {
    val rawPage = ownerDao.findByLastName(req, lastName)
    // group by owner.id, preserving sort order
    // aka "poor-man's ORM"
    val resultsMap = mutable.LinkedHashMap.empty[Int, Seq[OwnerWithPetRow]].withDefaultValue(Seq.empty)
    rawPage.rows.foreach { row =>
      val ownerId = row.o.id
      resultsMap(ownerId) = resultsMap(ownerId).appended(row)
    }
    val pageItems = resultsMap.map { case (_, rows) =>
      val ownerRow = rows.head.o
      val pets = rows.flatMap(_.p).map(pr => Pet.fromRow(pr, "", Seq.empty))
      Owner.fromRow(ownerRow, pets)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
