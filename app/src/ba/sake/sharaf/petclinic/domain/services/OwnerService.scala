package ba.sake.sharaf.petclinic.domain.services

import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.daos.OwnerDao
import ba.sake.sharaf.petclinic.domain.models.*

class OwnerService(ownerDao: OwnerDao) {

  def findByLastName(req: PageRequest, lastName: String): PageResponse[Owner] = {
    val rawPage = ownerDao.findByLastName(req, lastName)
    val pageItems = rawPage.rows.map { o =>
      Owner(
        o.id,
        o.first_name.getOrElse(""),
        o.last_name.getOrElse(""),
        o.address.getOrElse(""),
        o.city.getOrElse(""),
        o.telephone.getOrElse(""),
        Seq.empty
      )
    }
    PageResponse(pageItems, req.number, rawPage.total)
  }
}
