package ba.sake.sharaf.petclinic.web.models

import ba.sake.querson.QueryStringRW
import ba.sake.sharaf.petclinic.common.PageRequest

case class FindOwnerQP(
    p: PageRequest = PageRequest.first,
    q: Option[String] = None
) derives QueryStringRW
