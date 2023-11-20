package ba.sake.sharaf.petclinic.domain.models

case class Vet(
    firstName: String,
    lastName: String,
    specialties: Seq[String]
) {
  def fullName = s"${firstName} ${lastName}"
}
