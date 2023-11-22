package ba.sake.sharaf.petclinic.domain.services

import scala.collection.mutable
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.owner.*
import ba.sake.sharaf.petclinic.db.models.pet.*
import ba.sake.sharaf.petclinic.db.daos.OwnerDao
import ba.sake.sharaf.petclinic.domain.models.*

class OwnerService(ownerDao: OwnerDao) {

  def insert(owner: Owner): Owner =
    val ownerRow = ownerDao.insert(owner.toRow) // new id assigned :)
    owner.copy(id = ownerRow.id)

  def update(owner: Owner): Unit =
    ownerDao.update(owner.toRow)

  def findById(id: Int): Option[Owner] = {
    val rawRows = ownerDao.findById(id)
    if rawRows.isEmpty then None
    else {
      val ownerRow = rawRows.head.o

      // group by pet.id, preserving sort order
      // aka "poor-man's ORM"
      val petsMap = mutable.LinkedHashMap.empty[Int, (PetRow, Seq[OwnerPetVisitRow])]
      rawRows.foreach { row =>
        row.p.foreach { petRow =>
          val petId = petRow.id
          val (_, petRows) = petsMap.getOrElse(petId, (petRow, Seq.empty))
          petsMap(petId) = (petRow, petRows.appended(row))
        }
      }

      val pets = petsMap.map { case (petId, (petRow, rows)) =>
        val visits = rows.flatMap { row =>
          row.v.map { visitRow =>
            Visit(visitRow.visit_date, visitRow.description)
          }
        }
        Pet.fromRow(petRow, visits)
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
      val pets = rows.flatMap(_.p).map(pr => Pet.fromRow(pr, Seq.empty))
      Owner.fromRow(ownerRow, pets)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
