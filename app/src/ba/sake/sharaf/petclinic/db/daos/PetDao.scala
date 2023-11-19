package ba.sake.sharaf.petclinic.db.daos

import ba.sake.squery.*
import ba.sake.sharaf.petclinic.db.models.*

class PetDao(ctx: SqueryContext) {

  def findPetTypes(): Seq[PetTypeRow] = ctx.run {
    sql"""
      SELECT id, name
      FROM types
      ORDER BY name
    """.readRows[PetTypeRow]()
  }

  def findById(id: Int, ownerId: Int): Seq[PetRow] = ctx.run {
    sql"""
      SELECT id, name, birth_date, type_id, owner_id
      FROM pets
      WHERE id = $id AND owner_id = $ownerId
    """.readRows[PetRow]()
  }

}
