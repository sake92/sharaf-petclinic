package ba.sake.sharaf.petclinic.domain.services

import scala.collection.mutable
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*
import ba.sake.sharaf.petclinic.db.daos.VetDao
import ba.sake.sharaf.petclinic.domain.models.*

class VetService(vetDao: VetDao) {

  def findAll(req: PageRequest): PageResponse[Vet] = {

    val rawPage = vetDao.findAll(req)
    // group by vet.id, preserving sort order
    val resultsMap = mutable.LinkedHashMap.empty[Int, Seq[VetWithSpecialtyRow]].withDefaultValue(Seq.empty)
    rawPage.rows.map { vvs =>
      val vetId = vvs.v.id
      resultsMap(vetId) = resultsMap(vetId).appended(vvs)
    }
    val pageItems = resultsMap.map { case (k, rows) =>
      val vetRow = rows.head.v
      val specialties = rows.flatMap(_.s.flatMap(_.name))
      Vet(vetRow.first_name.getOrElse(""), vetRow.last_name.getOrElse(""), specialties)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
