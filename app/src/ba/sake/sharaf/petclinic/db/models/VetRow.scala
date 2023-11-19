package ba.sake.sharaf.petclinic.db.models

import ba.sake.squery.SqlReadRow

case class VetRow(
    id: Int,
    first_name: Option[String],
    last_name: Option[String]
) derives SqlReadRow:

  def fullName = s"""${first_name.getOrElse("")} ${last_name.getOrElse("")}"""
