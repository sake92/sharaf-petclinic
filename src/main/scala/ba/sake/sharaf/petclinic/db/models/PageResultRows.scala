package ba.sake.sharaf.petclinic.db.models

case class PageResultRows[T](rows: Seq[T], total: Int)
