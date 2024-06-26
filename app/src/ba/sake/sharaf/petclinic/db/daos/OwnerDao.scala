package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.{*, given}
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*, owner.*

class OwnerDao {

  def insert(o: OwnerRow): DbAction[OwnerRow] =
    sql"""
      INSERT INTO owners(first_name, last_name, address, city, telephone)
      VALUES (${o.first_name}, ${o.last_name}, ${o.address}, ${o.city}, ${o.telephone})
      RETURNING id, first_name, last_name, address, city, telephone
    """.insertReturningRow[OwnerRow]()

  def update(o: OwnerRow): DbAction[Unit] =
    sql"""
      UPDATE owners
      SET first_name =  ${o.first_name},
          last_name = ${o.last_name},
          address = ${o.address},
          city = ${o.city},
          telephone = ${o.telephone}
      WHERE id = ${o.id}
    """.update()

  def findById(id: Int): DbAction[Seq[OwnerPetVisitRow]] =
    sql"""
      SELECT  o.id, o.first_name, o.last_name, o.address, o.city, o.telephone,
              p.id, p.name, p.birth_date, t.name AS "p.pet_type",
              v.id, v.visit_date, v.description
      FROM owners o
      LEFT JOIN pets p ON p.owner_id = o.id
      LEFT JOIN types t ON t.id = p.type_id
      LEFT JOIN visits v ON v.pet_id = p.id
      WHERE o.id = ${id}
    """.readRows()

  def findByLastName(req: PageRequest, lastName: String): DbAction[PageResultRows[OwnerPetRow]] =
    val likeArg = s"${lastName}%"
    val query = sql"""
      WITH owners_slice AS (
        SELECT * from owners
        WHERE last_name ILIKE ${likeArg}
        ORDER BY id ASC
        LIMIT ${req.limit}
        OFFSET ${req.offset}
      )
      SELECT o.id, o.first_name, o.last_name, o.address, o.city, o.telephone,
              p.id, p.name, p.birth_date, t.name AS "p.pet_type"
      FROM owners_slice o
      LEFT JOIN pets p ON p.owner_id = o.id
      LEFT JOIN types t ON t.id = p.type_id
    """
    val items = query.readRows[OwnerPetRow]()
    val total = sql"SELECT COUNT(*) FROM owners WHERE last_name ILIKE ${likeArg}".readValue[Int]()
    PageResultRows(items, total)

}
