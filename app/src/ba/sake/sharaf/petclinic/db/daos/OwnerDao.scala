package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*

class OwnerDao(ctx: SqueryContext) {

  def findByLastName(req: PageRequest, lastName: String): PageResultRows[OwnerRow] = ctx.run {
    val likeArg = s"${lastName}%"
    val query = sql"""
      SELECT id, first_name, last_name, address, city, telephone
      FROM owners
      WHERE last_name ILIKE ${likeArg}
    """ ++ pageReqQuery(req)
    val items = query.readRows[OwnerRow]()
    val total = sql"SELECT COUNT(*) FROM owners WHERE last_name ILIKE ${likeArg}".readValue[Int]()
    PageResultRows(items, total)
  }

  private def pageReqQuery(req: PageRequest): Query =
    sql"""
      ORDER BY first_name ASC
      LIMIT ${req.limit}
      OFFSET ${req.offset}
    """
}
