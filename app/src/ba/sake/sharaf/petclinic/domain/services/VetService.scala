package ba.sake.sharaf.petclinic.domain.services

import ba.sake.squery.utils.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.vet.*
import ba.sake.sharaf.petclinic.db.daos.VetDao
import ba.sake.sharaf.petclinic.domain.models.*

class VetService(vetDao: VetDao) {

  def findAll(req: PageRequest): PageResponse[Vet] = {

    val rawPage = vetDao.findAll(req)
    val resultsMap = rawPage.rows.groupByOrderedOpt(_.v, _.s)
    val pageItems = resultsMap.map { case (vetRow, specRows) =>
      val specialties = specRows.flatMap(_.name)
      Vet(vetRow.first_name, vetRow.last_name, specialties)
    }.toSeq

    PageResponse(pageItems, req.number, rawPage.total)
  }
}
