package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*

class VetDao(ctx: SqueryContext) {

  def findAll(req: PageRequest): Page[VetRow] = ctx.runTransaction {
    val query = sql"""
      SELECT id, first_name, last_name
	  FROM vets
    """ ++ pageReqQuery(req)
    val items = query.readRows[VetRow]()
    val total = sql"SELECT COUNT(*) FROM vets".readValue[Int]()
    Page(items, req.number, total)
  }

  private def pageReqQuery(req: PageRequest): Query =
    sql"""
        ORDER BY id
        DESC LIMIT ${req.limit}
        OFFSET ${req.offset}
    """
}
