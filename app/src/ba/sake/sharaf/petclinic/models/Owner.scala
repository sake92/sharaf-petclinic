package ba.sake.sharaf.petclinic.models

case class Owner(
    id: Int,
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String
) {
  def fullName = s"${firstName} ${lastName}"
}
