package ba.sake.sharaf.petclinic.models

case class Vet(
    firstName: String,
    lastName: String,
    specialties: Seq[String]
) {
  def fullName = s"${firstName} ${lastName}"
}
