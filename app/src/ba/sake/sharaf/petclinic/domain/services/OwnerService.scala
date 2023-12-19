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
    val ownersMap = rawRows.groupByOrderedOpt(_.o, r => (r.p.map(_ -> r.v)))
    ownersMap.map { case (ownerRow, petAndVisitRows) =>
      val petsMap = petAndVisitRows.groupByOrderedOpt(_._1, _._2)
      val pets = petsMap.map { case (petRow, visitRows) =>
        val visits = visitRows.map(Visit.fromRow)
        Pet.fromRow(petRow, visits)
      }
      Owner.fromRow(ownerRow, pets.toSeq)
    }.headOption
  }

  def findByLastName(req: PageRequest, lastName: String): PageResponse[Owner] = {
    val rawPage = ownerDao.findByLastName(req, lastName)
    val ownersMap = rawPage.rows.groupByOrderedOpt(_.o, _.p)
    val pageItems = ownersMap.map { case (ownerRow, petRows) =>
      val pets = petRows.map(pr => Pet.fromRow(pr, Seq.empty))
      Owner.fromRow(ownerRow, pets)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
