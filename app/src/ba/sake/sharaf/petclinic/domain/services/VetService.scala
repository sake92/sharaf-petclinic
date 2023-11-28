package ba.sake.sharaf.petclinic.domain.services

import ba.sake.squery.utils.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.vet.*
import ba.sake.sharaf.petclinic.db.daos.VetDao
import ba.sake.sharaf.petclinic.domain.models.*

class VetService(vetDao: VetDao) {

  def findAll(req: PageRequest): PageResponse[Vet] = {

    val rawPage = vetDao.findAll(req)
    val resultsMap = rawPage.rows.groupByOrdered(r => (r.v.id, r.v))
    val pageItems = resultsMap.map { case (k, (vetRow, rows)) =>
      val specialties = rows.flatMap(_.s.flatMap(_.name))
      Vet(vetRow.first_name, vetRow.last_name, specialties)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
