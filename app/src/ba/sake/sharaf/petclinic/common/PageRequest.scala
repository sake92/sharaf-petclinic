package ba.sake.sharaf.petclinic.common

import ba.sake.validson.Validator
import ba.sake.querson.QueryStringRW

case class PageRequest(number: Int = 0) derives QueryStringRW:
  def offset: Int = number * Page.size
  def limit: Int = Page.size

object PageRequest:

  val first = PageRequest(0)

  given Validator[PageRequest] = Validator
    .derived[PageRequest]
    .and(_.number, _ >= 0, "must be >= 0")
  
