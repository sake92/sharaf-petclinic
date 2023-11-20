package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*

class VetDao(ctx: SqueryContext) {

  def findAll(req: PageRequest): PageResultRows[VetWithSpecialtyRow] = ctx.run {
    val query = sql"""
      SELECT v.id, v.first_name, v.last_name, s.id, s.name
	    FROM vets v
      LEFT JOIN vet_specialties vs ON vs.vet_id = v.id
      LEFT JOIN specialties s ON s.id = vs.specialty_id
    """ ++ pageReqQuery(req)
    val items = query.readRows[VetWithSpecialtyRow]()
    val total = sql"SELECT COUNT(*) FROM vets".readValue[Int]()
    PageResultRows(items, total)
  }

  private def pageReqQuery(req: PageRequest): Query =
    sql"""
      LIMIT ${req.limit}
      OFFSET ${req.offset}
    """
}
