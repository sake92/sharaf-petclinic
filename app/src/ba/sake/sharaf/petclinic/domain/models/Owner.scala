package ba.sake.sharaf.petclinic.domain.models

case class Owner(
    id: Int,
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String,
    pets: Seq[String]
) {
  def fullName = s"${firstName} ${lastName}"
}
