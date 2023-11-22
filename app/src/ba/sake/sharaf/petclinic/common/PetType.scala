package ba.sake.sharaf.petclinic.common

import ba.sake.formson.FormDataRW

enum PetType derives FormDataRW:
  case cat, dog, lizard, snake, bird, hamster

object PetType:
  val sortedValues: Seq[String] =
    PetType.values.toSeq.map(_.toString).sorted
