package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.*
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*, owner.*

class OwnerDao(ctx: SqueryContext) {

  def findByLastName(req: PageRequest, lastName: String): PageResultRows[OwnerWithPetRow] = ctx.run {
    val likeArg = s"${lastName}%"
    val query = sql"""
      SELECT o.id, o.first_name, o.last_name, o.address, o.city, o.telephone,
              p.id, p.name, p.birth_date
      FROM owners o
      LEFT JOIN pets p ON p.owner_id = o.id
      WHERE last_name ILIKE ${likeArg}
    """ ++ pageReqQuery(req)
    val items = query.readRows[OwnerWithPetRow]()
    val total = sql"SELECT COUNT(*) FROM owners WHERE last_name ILIKE ${likeArg}".readValue[Int]()
    PageResultRows(items, total)
  }

  private def pageReqQuery(req: PageRequest): Query =
    sql"""
      LIMIT ${req.limit}
      OFFSET ${req.offset}
    """
}
