package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.{*, given}
import ba.sake.sharaf.petclinic.db.models.pet.*

class PetDao() {

  def insert(ownerId: Int, petTypeId: Int, p: PetRow): DbAction[Unit] =
    sql"""
      INSERT INTO pets(owner_id, type_id, name, birth_date)
      VALUES (${ownerId}, ${petTypeId}, ${p.name}, ${p.birth_date})
    """.insert()

  def update(petTypeId: Int, p: PetRow): DbAction[Unit] =
    sql"""
      UPDATE pets
      SET type_id = ${petTypeId},
          name = ${p.name},
          birth_date = ${p.birth_date}
      WHERE id = ${p.id}
    """.update()

  def findPetTypes(): DbAction[Seq[PetTypeRow]] =
    sql"""
      SELECT id, name
      FROM types
      ORDER BY name
    """.readRows[PetTypeRow]()

  def findById(ownerId: Int, id: Int): DbAction[Option[PetRow]] =
    // column names must exactly match PetRow fields
    sql"""
      SELECT p.id AS id, p.name AS name, birth_date, t.name AS pet_type
      FROM pets p
      JOIN types t ON t.id = type_id
      WHERE p.id = $id AND owner_id = $ownerId
    """.readRowOpt[PetRow]()

  def insertVisit(petId: Int, v: VisitRow): DbAction[Unit] =
    sql"""
      INSERT INTO visits(pet_id, visit_date, description)
      VALUES (${petId}, ${v.visit_date}, ${v.description})
    """.insert()

}
