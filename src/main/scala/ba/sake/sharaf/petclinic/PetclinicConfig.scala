package ba.sake.sharaf.petclinic

import ba.sake.tupson.JsonRW

case class PetclinicConfig(
    port: Int,
    baseUrl: String,
    db: DatabaseConfig
) derives JsonRW

case class DatabaseConfig(
    jdbcUrl: String,
    username: String,
    password: String
) derives JsonRW
