package ba.sake.sharaf.petclinic.domain.services

import ba.sake.squery.utils.*
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
    rawRows.headOption.map { firstRow =>

      val petsMap = rawRows.groupByOrderedOpt(r => r.p.map(pet => (pet.id, pet)))
      val pets = petsMap.map { case (petId, (petRow, petRows)) =>
        val visits = for
          row <- petRows
          visitRow <- row.v
        yield Visit(visitRow.visit_date, visitRow.description)
        Pet.fromRow(petRow, visits)
      }

      val ownerRow = firstRow.o
      Owner.fromRow(ownerRow, pets.toSeq)
    }
  }

  def findByLastName(req: PageRequest, lastName: String): PageResponse[Owner] = {
    val rawPage = ownerDao.findByLastName(req, lastName)

    val ownersMap = rawPage.rows.groupByOrdered(r => (r.o.id, r.o))
    val pageItems = ownersMap.map { case (_, (ownerRow, ownerRows)) =>
      val pets = ownerRows.flatMap(_.p).map(pr => Pet.fromRow(pr, Seq.empty))
      Owner.fromRow(ownerRow, pets)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
