package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.{*, given}
import ba.sake.sharaf.petclinic.common.*
import ba.sake.sharaf.petclinic.db.models.*, vet.*

class VetDao() {

  def findAll(req: PageRequest): DbAction[PageResultRows[VetSpecialtyRow]] =
    val query = sql"""
      WITH vets_slice AS (
        SELECT * from vets
        LIMIT ${req.limit}
        OFFSET ${req.offset}
      )
      SELECT v.id, v.first_name, v.last_name, s.id, s.name
	    FROM vets_slice v
      LEFT JOIN vet_specialties vs ON vs.vet_id = v.id
      LEFT JOIN specialties s ON s.id = vs.specialty_id
    """
    val items = query.readRows[VetSpecialtyRow]()
    val total = sql"SELECT COUNT(*) FROM vets".readValue[Int]()
    PageResultRows(items, total)

}
