package ba.sake.sharaf.petclinic.domain.services

import scala.collection.mutable
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.owner.OwnerWithPetRow
import ba.sake.sharaf.petclinic.db.daos.OwnerDao
import ba.sake.sharaf.petclinic.domain.models.*

class OwnerService(ownerDao: OwnerDao) {

  def findByLastName(req: PageRequest, lastName: String): PageResponse[Owner] = {
    val rawPage = ownerDao.findByLastName(req, lastName)
    // group by owner.id, preserving sort order
    // aka "poor-man's ORM"
    val resultsMap = mutable.LinkedHashMap.empty[Int, Seq[OwnerWithPetRow]].withDefaultValue(Seq.empty)
    rawPage.rows.map { row =>
      val vetId = row.o.id
      resultsMap(vetId) = resultsMap(vetId).appended(row)
    }
    val pageItems = resultsMap.map { case (_, rows) =>
      val ownerRow = rows.head.o
      val pets = rows.flatMap(_.p.flatMap(_.name))
      Owner(
        ownerRow.id,
        ownerRow.first_name.getOrElse(""),
        ownerRow.last_name.getOrElse(""),
        ownerRow.address.getOrElse(""),
        ownerRow.city.getOrElse(""),
        ownerRow.telephone.getOrElse(""),
        pets
      )
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
