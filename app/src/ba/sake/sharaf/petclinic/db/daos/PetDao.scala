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
}
