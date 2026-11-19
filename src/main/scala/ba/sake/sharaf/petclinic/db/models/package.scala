package ba.sake.sharaf.petclinic.db.models

import java.sql.ResultSet
import ba.sake.squery.read.SqlRead
import ba.sake.sharaf.petclinic.common.PetType

// TODO derive SqlRead automatically
given SqlRead[PetType] with {
  private val stringRead = SqlRead[String]

  override def readByName(jRes: ResultSet, colName: String): Option[PetType] =
    stringRead.readByName(jRes, colName).map(PetType.valueOf)

  override def readByIdx(jRes: ResultSet, colIdx: Int): Option[PetType] =
    stringRead.readByIdx(jRes, colIdx).map(PetType.valueOf)
}
